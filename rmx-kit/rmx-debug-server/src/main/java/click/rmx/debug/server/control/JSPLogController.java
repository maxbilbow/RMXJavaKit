package click.rmx.debug.server.control;

import click.rmx.debug.RMXException;
import click.rmx.debug.server.model.Log;
import click.rmx.debug.server.repository.LogRepository;
import click.rmx.debug.server.service.LogService;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by Max on 25/10/2015.
 */
@Controller
public class JSPLogController {


    @Resource
    private LogService service;

    @Resource//(type = LogRepository.class)
    private LogRepository repository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String get(ModelMap model)
    {
        model.addAttribute("logs", repository.getMessages());
        model.addAttribute("errors", repository.getErrors());
        model.addAttribute("warnings", repository.getWarnings());
        model.addAttribute("status",
                service.isActive() ?
                        "<span style=\"color: green\">SERVER IS ON</span>" :
                        "<span style=\"color: red\">SERVER IS OFF</span>"
        );
        model.addAttribute("connect", service.isActive() ? "Stop" : "Start");
        return "version2";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String start(ModelMap model)
    {
        if (service.isActive()) {
            try {
                if(service.closeServer())
                   repository.save(
                           service.makeLog("Server was closed")
                   );
                else
                    repository.save(
                            service.makeWarning("Debug server may not have closed properly")
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
                                RMXException.unexpected(e,"Rabbit Topic server failed.")
                        )
                );
            }
        }
        return get(model);
    }



    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public String  postLog(ModelMap model, HttpPost data)//@ModelAttribute("body") Object data)
    {
        Log log = service.makeLog(data);
        log.setMessage(log.getMessage() + " >> CLASS: " + data.getClass().getName());
        repository.save(log);
        service.notifySubscribers(log);
        return get(model);
    }

//    @RequestMapping(value = "/post",method = RequestMethod.GET)
//    public String getPostLog(ModelMap model)
//    {
//        return get(model);
//    }

}
