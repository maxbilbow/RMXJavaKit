package main.java.uk.co.utilisoft.utilities;

//import org.apache.log4j.Logger;


/**
 * Created by bilbowm (Max Bilbow) on 18/12/2015.
 * THis ONLY should effect websocket output.
 */
public abstract class LoggerProperties
{

    public static String level = "info";// = Logger.Level.INFO.toString();




    public static final String INFO = "info",WARN="warn",ERROR="error",NONE="none";


    public String setLevel(String level)
    {

//        if (instance == null)
//            return "Debug Level Unknown";
        level = level.toLowerCase();
        if (level.startsWith("debug-"))
            level = level.substring("debug-".length());

        LoggerProperties.level = level;

        return level;

    }




}
