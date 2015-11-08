package click.rmx.debug.server.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by bilbowm on 04/11/2015.
 */
@Controller
@RequestMapping("/live")
public class LiveUpdatesController {

    @RequestMapping(method = RequestMethod.GET)
    public String get()
    {
        return "live-updates";
    }
}
