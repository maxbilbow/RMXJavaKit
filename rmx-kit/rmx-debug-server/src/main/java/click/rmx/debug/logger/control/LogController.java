package click.rmx.debug.logger.control;

import click.rmx.debug.RMXException;
import click.rmx.debug.logger.repository.LogRepository;
import click.rmx.debug.logger.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Max on 25/10/2015.
 */
@Controller
public class LogController
{
  @Resource
  private LogService service;// = LogService.getInstance();

  @Autowired//(type = LogRepository.class)
  private LogRepository repository;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public ModelAndView get(ModelAndView model, HttpServletRequest request)
  {
    if (model == null)
      model = new ModelAndView();
    else
      model.setViewName("version2");
    model.addObject("logs", repository.getMessages());
    model.addObject("errors", repository.getErrors());
    model.addObject("warnings", repository.getWarnings());
    model.addObject("contextPath", request.getContextPath());
    model.addObject("status",
            service.isActive() ?
                    "<span style=\"color: green\">SERVER IS ON</span>" :
                    "<span style=\"color: red\">SERVER IS OFF</span>"
    );
    model.addObject("connect", service.isActive() ? "Stop" : "Start");

    return model;
  }

  @RequestMapping(value = "/index", method = RequestMethod.POST)
  public ModelAndView start(ModelAndView model, HttpServletRequest request)
  {
    if (service.isActive())
    {
      try
      {
        if (service.closeServer())
          repository.save(
                  service.makeLog("Server was closed")
          );
        else
          repository.save(
                  service.makeWarning("Debug logger may not have closed properly")
          );
      } catch (RMXException e)
      {
        repository.save(
                service.makeException(e)//,"Server could not be closed! >> ")
        );

      }
    } else
    {
      //Start rabbitMQ Debug log receiver
      try
      {
        service.startDebugExchange();
        repository.save(
                service.makeLog("RabbitMQ Topic Server Started")
        );
      } catch (Exception e)
      {
        e.printStackTrace();
        repository.save(
                service.makeException(
                        RMXException.unexpected(e, "Rabbit Topic logger failed.")
                )
        );
      }
    }

    return get(model, request);// "version2";
  }




}
