package click.rmx.debug.logger.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bilbowm (Max Bilbow) on 25/11/2015.
 */
@Controller
@RequestMapping({"/pages","/pages/index"})
public class GenericController {

    @RequestMapping(method = RequestMethod.GET)
    public String get(@RequestParam(value = "p",required = false,defaultValue = "forward:/live/") String page, ModelMap model, HttpServletRequest request)
    {
        if (page.startsWith("forward:"))
            return page;

        model.addAttribute("contextPath",request.getContextPath());
        return page;
    }
}
