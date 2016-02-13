package click.rmx.web;

import click.rmx.util.OSValidator;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class Browser {

    private Logger mLogger = Logger.getLogger(Browser.class);

    public void launch()
    {
        final String port = System.getProperty("server.port");
        final String url = "localhost:"+ (port != null ? port : "80");
        launch(url);
    }

    /**
     * Launch on localhost:port
     * @param port
     */
    public void launch(int port)
    {
        final String url = "localhost:"+ port;
        launch(url);
    }

    public void launch(String address) {
        final String url;
        if (address.startsWith("http"))
            url = address;
        else
            url = "http://"+address;


        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                mLogger.error(e);
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            String os = System.getProperty("os.name");
            if (OSValidator.isWindows()) {
                final String[] cmd = new String[4];
                cmd[0] = "cmd.exe";
                cmd[1] = "/C";
                cmd[2] = "start";
                cmd[3] = url;
                try {
                    runtime.exec(cmd);
                } catch (IOException e) {
                    mLogger.error(e);
                }
            }
            else {
                String cmd = OSValidator.isMac() ? "open " : "xdg-open ";
                try {
                    runtime.exec(cmd + url);
                } catch (IOException e) {
                    mLogger.error(e);
                }
            }
        }
    }
}