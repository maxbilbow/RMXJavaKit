package click.rmx.debug.server.control;

import click.rmx.debug.RMXException;
import click.rmx.debug.server.repository.LogRepository;
import click.rmx.debug.server.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
    public String get(ModelMap model) {
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
    public String start(ModelMap model) {
        if (service.isActive()) {
            try {
                if (service.closeServer())
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
                                RMXException.unexpected(e, "Rabbit Topic server failed.")
                        )
                );
            }
        }
        return get(model);
    }


//    @RequestMapping(value = "/postString",method = RequestMethod.POST)
//    public String  postLogString(@RequestParam String message, HttpPost post)//@ModelAttribute("body") Object data)
//    {
//        {
//            Log log = service.makeLog(message);
//            repository.save(log);
//            service.notifySubscribers(log);
//        }
//        {
//            Log log = service.makeLog(post);
//            repository.save(log);
//            service.notifySubscribers(log);
//        }
//        return get(new ModelMap());
//    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String postLog(HttpServletRequest post)//, @RequestBody FlowFile body)//@ModelAttribute("body") Object data)
    {

//            service.notifySubscribers(
////                    service.makeLog(body),
//                    service.makeLog(post),
//                    service.makeLog(post.getHeaderNames())
//            );

        try {
            byte[] bytes = new byte[post.getContentLength()];
            post.getInputStream().read(bytes);
            service.notifySubscribers(
                    service.makeLog(bytes)
            );
        } catch (Exception e) {
            service.notifySubscribers(
                service.makeException(e)
            );
        }

//        {
//            List<Locator.BytesLocator> bytesFound = findBytesIn(post);//, Locale.class);
//            {
//                Log log;
//                String msg = "BYTES FOUND:";
//                if (bytesFound.size() > 0) {
//                    for (Locator.BytesLocator bytes : bytesFound)
//                        msg += " | " + bytes;
//                    log = service.makeLog(msg);
//                } else {
//                    log = service.makeLog("No Bytes Found");
//                }
//                service.notifySubscribers(log);
//                service.save(log);
//            }
//        }
        return get(new ModelMap());
    }

//    @RequestMapping(value = "/post",method = RequestMethod.GET)
//    public String getPostLog(ModelMap model)
//    {
//        return get(model);
//    }


}
