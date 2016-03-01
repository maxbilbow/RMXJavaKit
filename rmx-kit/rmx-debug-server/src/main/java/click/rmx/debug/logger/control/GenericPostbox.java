package click.rmx.debug.logger.control;


import click.rmx.debug.logger.service.LogService;
import click.rmx.util.ObjectInspector;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Max on 22/02/2016.
 */
@Controller
@RequestMapping({"/post","/post/index"})
public class GenericPostbox
{
  @Resource
  private LogService service;// = LogService.getInstance();

  Logger mLogger = Logger.getLogger(getClass());

  ObjectInspector mInspector = new ObjectInspector();

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView post()//, @RequestBody FlowFile body)//@ModelAttribute("body") Object data)
  {
    return new ModelAndView("console");
  }

  @RequestMapping(method = RequestMethod.POST)
  public
  @ResponseBody
  Object postLog(HttpServletRequest post)//, @RequestBody FlowFile body)//@ModelAttribute("body") Object data)
  {
    try
    {
      byte[] bytes = new byte[post.getContentLength()];
      post.getInputStream().read(bytes);
      service.notifySubscribers(
              service.makeLog(bytes)
      );
      return "Received data with size: " + bytes.length;
    } catch (Exception e)
    {
      service.notifySubscribers(
              service.makeException(e)
      );
      return "Failed to get message: " + e;
    }
  }
}
