package click.rmx.debug.logger.control;


import click.rmx.debug.logger.service.LogService;
import click.rmx.util.ObjectInspector;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Max on 22/02/2016.
 */
@Controller
public class GenericPostbox
{
  @Resource
  LogService mLogService;

  Logger mLogger = Logger.getLogger(getClass());

  ObjectInspector mInspector = new ObjectInspector();

  @RequestMapping(value = "bol",method = RequestMethod.POST)
  public @ResponseBody String postBol(
          @RequestParam("bol") Object aBol)
  {

    mLogger.info(mInspector.stringify(aBol));

    return "Thank you!";
  }
}
