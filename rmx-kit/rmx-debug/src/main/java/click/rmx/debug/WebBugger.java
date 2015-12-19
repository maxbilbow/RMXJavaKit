package click.rmx.debug;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static click.rmx.debug.Bugger.timestamp;

/**
 * Created by bilbowm on 13/10/2015.
 */
public class WebBugger {

    public static final String DEBUG_EXCHANGE_NAME = "debug_topic_exchange";

    private static WebBugger instance;

    public static WebBugger getInstance()
    {
        return instance != null ? instance : (instance = new WebBugger());
    }

    private WebBugger()
    {
        final WebBugger b = this;
    }


    private final LinkedList<String> errors = new LinkedList<>();

    private final LinkedList<String> logs = new LinkedList<>();

    public Collection<String> getErrors() {
        return errors;
    }

    public String getErrorHtml() {
            String log = "<ul>";// + this.errorLog;
            for (String error : errors) {
                log += "<li>" + error.replace("\n","<br/>") + "</li>";
            }
            return log + "</ul>";//.replace("\n", "<br/>");
    }

    public void addLog(String log) {
        this.logs.addFirst("<span style=\"color: green;\">LOG ["+timestamp()+"] >> </span>" + toHtml(log));
    }

    public void addException(String message)
    {
        this.errors.addFirst("<span style=\"color: red;\">ERR ["+timestamp()+"] >> </span>" + toHtml(message));
    }


    public void addException(Exception e, String message)
    {
        this.addException(RMXException.unexpected(e, message, 1));
    }

    public void addException(Exception e)
    {
        this.addException(RMXException.unexpected(e,1));
    }

    public void addException(RMXException e) {
        this.errors.add("<span style=\"color: red;\">ERR >> </span>" + e.html());
    }

    public static String toHtml(String string)
    {
        return string
                .replace("\n", "<br/>")
                .replace("COMPLETED", "<span style=\"color=green;\">COMPLETED</span>")
                .replace("FAILED", "<span style=\"color=red;\">FAILED</span>");
    }


    public List<String> getLogs() {
        return logs;
    }

    public List<String> getLogs(int limit) {
        int max = upTo(logs,limit);
        return logs.subList(0,max > 0 ? max : 0);
    }

    private int upTo(LinkedList<String> logs, int i) {
        return logs.size() <= i ? logs.size() - 1 : i - 1;
    }

    public void addException(Exception e, Thread t) {
        this.addException(RMXException.unexpected(e, "FAILED WITH THREAD: " + t.getName()));
    }

    public void addFunException(String message) {
        this.addException(RMXException.newInstance(message, RMXError.JustForFun));
    }

}
