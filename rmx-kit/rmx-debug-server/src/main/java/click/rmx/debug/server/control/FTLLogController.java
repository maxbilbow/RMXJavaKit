package click.rmx.debug.server.control;

import click.rmx.debug.RMXException;
import click.rmx.debug.server.repository.LogRepository;
import click.rmx.debug.server.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by Max on 25/10/2015.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/version3.html")
public class FTLLogController {


    @Resource
    private LogService service;

    @Resource//(type = LogRepository.class)
    private LogRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get()//(ModelMap model)
    {
        ModelAndView model = new ModelAndView("version3");
        model.addObject("logs", repository.getMessages());
        model.addObject("errors", repository.getErrors());
        model.addObject("warnings", repository.getWarnings());
        model.addObject("status",
                service.isActive() ?
                        "<span style=\"color: green\">SERVER IS ON</span>" :
                        "<span style=\"color: red\">SERVER IS OFF</span>"
        );
        model.addObject("connect", service.isActive() ? "Stop" : "Start");
        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView start()
    {
        if (service.isActive()) {
            try {
                if(service.closeServer())
                   repository.save(
                           service.addLog("Server was closed")
                   );
                else
                    repository.save(
                            service.addWarning("Debug server may not have closed properly")
                    );
            } catch (RMXException e) {
                repository.save(
                        service.addException(e)//,"Server could not be closed! >> ")
                        );

            }
        } else {
            //Start rabbitMQ Debug log receiver
            try {
                service.startDebugExchange();
                repository.save(
                        service.addLog("RabbitMQ Topic Server Started")
                );
            } catch (Exception e) {
                e.printStackTrace();
                repository.save(
                        service.addException(
                                RMXException.unexpected(e,"Rabbit Topic server failed.")
                        )
                );
            }
        }
        return get();
    }
}
