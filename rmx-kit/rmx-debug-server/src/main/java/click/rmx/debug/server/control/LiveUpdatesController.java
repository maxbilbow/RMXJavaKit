package click.rmx.debug.server.control;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by bilbowm on 04/11/2015.
 */
@Controller
@RequestMapping({"/live"})
public class LiveUpdatesController {

    @RequestMapping(method = RequestMethod.GET)
    public String get(ModelMap model, HttpServletRequest request)
    {

//        Bugger.print(Bugger.inspectObject(request));
        String localhost = request.getRequestURL().toString()
                .replace("http://", "ws://")
                .replace("https://","wss://")
                .replace(request.getRequestURI(),request.getContextPath()+"/updates");
//                .replaceFirst("/%.html","/updates");
        model.addAttribute("hostNames",
                Arrays.asList(
                        localhost,
                        "ws://dev.maxbilbow.com:8080/debug-server/updates",
                        "ws://localhost:8080/debug-server/updates"
                ));
        return "live-updates";
    }
}
