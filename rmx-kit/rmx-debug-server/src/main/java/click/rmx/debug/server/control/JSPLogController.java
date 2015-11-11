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
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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


    @RequestMapping(value = "/postString",method = RequestMethod.POST)
    public String  postLogString(@RequestParam String message, HttpPost post)//@ModelAttribute("body") Object data)
    {
        {
            Log log = service.makeLog(message);
            repository.save(log);
            service.notifySubscribers(log);
        }
        {
            Log log = service.makeLog(post);
            repository.save(log);
            service.notifySubscribers(log);
        }
        return get(new ModelMap());
    }

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public String  postLog(HttpServletRequest post)//@ModelAttribute("body") Object data)
    {



                service.notifySubscribers(
                        service.makeLog(post.getContentType()),
                        service.makeLog(post.getSession())
                );

        List<BytesLocator> bytesFound = findBytesIn(post);//, Locale.class);
        {
            Log log;
            String msg = "BYTES FOUND:";
            if (bytesFound.size() > 0) {
                for (BytesLocator bytes : bytesFound)
                    msg += " | " + bytes;
                log = service.makeLog(msg);
            } else {
                log = service.makeLog("No Bytes Found");
            }
            service.notifySubscribers(log);
            service.save(log);
        }
        return get(new ModelMap());
    }

//    @RequestMapping(value = "/post",method = RequestMethod.GET)
//    public String getPostLog(ModelMap model)
//    {
//        return get(model);
//    }

    abstract class Locator<T> {
        public final String location;
        public final T result;

        Locator(String location, T result) {
            this.location = location;
            this.result = result;
        }
    }

    class BytesLocator extends Locator<byte[]> {

        BytesLocator(String location, byte[] result) {
            super(location, result);
        }

        @Override
        public String toString() {
            String toString = null;
            try {
                toString = new String(result);
            } catch (Exception e) {
                toString = "FAILED TO PARSE";
            }
            return toString;
//            return location + " == " + toString;
        }
    }
    public List<BytesLocator> findBytesIn(Object object, Class<?>... ignore)
    {
        return findBytesIn(object, null, null, null, 5, Arrays.asList(ignore));
    }

    private List<BytesLocator> findBytesIn(Object inObject, List<BytesLocator> bytesFound, List<Object> checked, String location, int limit, List<Class<?>> ignore)
    {

        if (location == null)
            location = "";
        if (checked == null)
            checked = new ArrayList<>();
        if (bytesFound == null)
            bytesFound = new ArrayList<>();
        if (inObject == null || inObject.getClass().isPrimitive())
            return bytesFound;
        if (checked.contains(inObject) || ignore.contains(inObject.getClass()))
            return bytesFound;
        else
            checked.add(inObject);
        Method[] methods = inObject.getClass().getMethods();
        for (Method m: methods) {
            if (m.getParameterCount() == 0 &&
                    (!m.getReturnType().isPrimitive() || m.getReturnType() == byte[].class) &&
                    m.getReturnType() != String.class &&
//                    !m.getReturnType().getClass().isSynthetic() &&
                    !m.getName().contains("copy") &&
                    !m.getName().contains("clone") &&
                    !m.getName().contains("getClass") &&
                    !m.getName().contains("getPackage")) {
                Object ret = null;
                try {
                    ret = m.invoke(inObject);
                } catch (Exception e) {
                    System.err.println("FAILED TO INVOKE: " + e);
                    break;
                }
                final String methodName = m.getName();
                final Class<?> returnType = m.getReturnType();
                final String newLocation = location + "." + methodName + "("+ returnType.getSimpleName() +")";

                if (returnType == byte[].class) {
                    bytesFound.add(
                            new BytesLocator(location + "." + m.getName() + "()", (byte[]) ret)
                    );
                } else if (ret == null) {
                    break;
                }else if (limit > 0 && ret.getClass().isAssignableFrom(Map.class)) {
                    Map map = (Map) ret;
                    final List<BytesLocator> bf = bytesFound;
                    final List<Object> ch = checked;
                    map.forEach((k,v) ->
                        findBytesIn(v, bf, ch, newLocation + "["+k+"]", limit - 1, ignore)
                    );
                } else if (limit > 0 && returnType.isArray() && !inObject.getClass().isArray()) {
                    for (int i = 0; i< Array.getLength(ret); ++i)
                        findBytesIn(Array.get(ret,i), bytesFound, checked, newLocation + "["+i+"]", limit - 1, ignore);
                } else {
                    findBytesIn(ret, bytesFound, checked, newLocation, limit, ignore);
//                    System.out.println(newLocation);
                }
            }
        }
        return bytesFound;

    }

}
