package click.rmx.debug.logger.control;

import click.rmx.debug.RMXException;
import click.rmx.debug.logger.repository.LogRepository;
import click.rmx.debug.logger.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Max on 25/10/2015.
 */
@RestController
public class LogController {


    @Autowired
    private LogService service;

    @Autowired//(type = LogRepository.class)
    private LogRepository repository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView get(ModelAndView model,HttpServletRequest request) {
        model.addObject("logs", repository.getMessages());
        model.addObject("errors", repository.getErrors());
        model.addObject("warnings", repository.getWarnings());
        model.addObject("contextPath",request.getContextPath());
        model.addObject("status",
                service.isActive() ?
                        "<span style=\"color: green\">SERVER IS ON</span>" :
                        "<span style=\"color: red\">SERVER IS OFF</span>"
        );
        model.addObject("connect", service.isActive() ? "Stop" : "Start");
        ModelAndView mv = model;//new ModelAndView("version2");
        mv.setViewName("version2");
//        mv.addAllObjects(model);
        return mv;//"version2";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView start(ModelAndView model, HttpServletRequest request) {
        if (service.isActive()) {
            try {
                if (service.closeServer())
                    repository.save(
                            service.makeLog("Server was closed")
                    );
                else
                    repository.save(
                            service.makeWarning("Debug logger may not have closed properly")
                    );
            } catch (RMXException e) {
                repository.save(
                        service.makeException(e)//,"Server could not be closed! >> ")
                );

            }
        } else {
            //Start rabbitMQ Debug log receiver
            try {
                service.startDebugExchange();
                repository.save(
                        service.makeLog("RabbitMQ Topic Server Started")
                );
            } catch (Exception e) {
                e.printStackTrace();
                repository.save(
                        service.makeException(
                                RMXException.unexpected(e, "Rabbit Topic logger failed.")
                        )
                );
            }
        }

        return get(model,request);// "version2";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public @ResponseBody Object postLog(HttpServletRequest post)//, @RequestBody FlowFile body)//@ModelAttribute("body") Object data)
    {
        try {
            byte[] bytes = new byte[post.getContentLength()];
            post.getInputStream().read(bytes);
            service.notifySubscribers(
                    service.makeLog(bytes)
            );
            return "Received data with size: " + bytes.length;
        } catch (Exception e) {
            service.notifySubscribers(
                service.makeException(e)
            );
            return "Failed to get message: " + e;
        }
    }



}
