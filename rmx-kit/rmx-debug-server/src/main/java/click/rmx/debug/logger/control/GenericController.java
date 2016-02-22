package click.rmx.debug.logger.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bilbowm (Max Bilbow) on 25/11/2015.
 */
@RestController
@RequestMapping({"/pages", "/pages/index"})
public class GenericController
{

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView get(@RequestParam(value = "p", required = false, defaultValue = "") String page, ModelAndView model, HttpServletRequest request)
  {
    model.addObject("contextPath", request.getContextPath());
    if (page.isEmpty())
      model.setViewName("live-updates-v2");// "forward:/live/";
    else
      model.setViewName(page);

    return model;//new ModelAndView(page);//.getView();
  }

}
