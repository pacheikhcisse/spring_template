package jsp;

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