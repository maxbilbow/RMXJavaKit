package click.rmx.debug.logger.control;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by bilbowm on 04/11/2015.
 */
@RestController
public class LiveUpdatesController {

    @RequestMapping(value = "live/old/index", method = RequestMethod.GET)
    public ModelAndView get(ModelAndView model, HttpServletRequest request)
    {
        model.setViewName("live-updates");
//        Bugger.print(Bugger.inspectObject(request));
        String localhost = request.getRequestURL().toString()
                .replace("http://", "ws://")
                .replace("https://","wss://")
                .replace(request.getRequestURI(),request.getContextPath()+"/updates");
//                .replaceFirst("/%.html","/updates");
        model.addObject("hostNames",
                Arrays.asList(
                        localhost,
                        "ws://dev.maxbilbow.com:8080/debug-server/updates"//,
//                        "ws://localhost:8080/debug-server/updates"
                ));
        return model;
    }

    @RequestMapping(value = "/live/index", method = RequestMethod.GET)
    public ModelAndView getV2(ModelAndView model, HttpServletRequest request)
    {
        get(model,request);
        model.setViewName("live-updates-v2");
        model.addObject("contextPath",request.getContextPath());
        return model;
    }

//    @RequestMapping(value = "/live", method = RequestMethod.GET)
//    public String getOriginal(ModelMap model,HttpServletRequest request){
//        return "redirect:live/index.html";
//    }
}
