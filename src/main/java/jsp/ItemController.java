package jsp;

/* import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;

public class ItemController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ItemController() {
        super();
    }

    /* Our code doesn't use any GET requests, so we instead do a POST request
    which will just list the items we have saved in the database. */
/*

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            ItemManager manager = ItemManagerFactory.getManager();

            if (request.getParameter("remove") != null) {
                String name = request.getParameter("removeName");
                manager.delete(name);

            } else if (request.getParameter("add") != null) {
                String name = request.getParameter("name");
                String comment = request.getParameter("comment");
                manager.add(name, comment);
            }
            request.setAttribute("items", manager.getItems());
            request.getRequestDispatcher("jsp/index.jsp").forward(request, response);

        // ItemManager methods can throw HibernateException, so we catch them with a standard error page.
        } catch (HibernateException e) {
            request.setAttribute("error", "Invalid query, please try again.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }
}
*/

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.hibernate.HibernateException;

@Controller
@RequestMapping("/")
public class ItemController {
 
    @RequestMapping(method = RequestMethod.GET)
    public String showItems(ModelMap model) {
        try {
            ItemManager manager = ItemManagerFactory.getManager();
            model.addAttribute("items", manager.getItems());
            return "index";
        } catch (HibernateException e) {
            return hibernateError(model);
        }
    }

    @RequestMapping(params = "add", method = RequestMethod.POST)
    public String addItem(@ModelAttribute("item") Item item, ModelMap model) {
        try {
            ItemManager manager = ItemManagerFactory.getManager();
            manager.add(item);
            return showItems(model);
        } catch (HibernateException e) {
            return hibernateError(model);
        }
    }

    @RequestMapping(params = "remove", method = RequestMethod.POST)
    public String removeItem(@RequestParam("removeName") String removeName, ModelMap model) {
        try {
            ItemManager manager = ItemManagerFactory.getManager();
            manager.delete(removeName);
            return showItems(model);
        } catch (HibernateException e) {
            return hibernateError(model);
        }
    }

    public String hibernateError(ModelMap model) {
        model.addAttribute("error", "Invalid query, please try again.");
        return "error";
    }
 
}