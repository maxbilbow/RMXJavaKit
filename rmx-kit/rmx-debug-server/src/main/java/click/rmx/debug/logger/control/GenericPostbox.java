package click.rmx.debug.logger.control;


import click.rmx.debug.logger.service.LogService;
import click.rmx.util.ObjectInspector;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Max on 22/02/2016.
 */
@RestController
public class GenericPostbox
{
  @Resource
  LogService mLogService;

  Logger mLogger = Logger.getLogger(getClass());

  ObjectInspector mInspector = new ObjectInspector();

  @RequestMapping(value = "/p1", method = RequestMethod.POST)
  public @ResponseBody Object post(
          HttpServletRequest post)//, @RequestBody FlowFile body)//@ModelAttribute("body") Object data)
  {
    System.exit(1);
    return null;
  }
}
