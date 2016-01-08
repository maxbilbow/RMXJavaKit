package click.rmx.web;

import click.rmx.os.OSValidator;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//import org.springframework.context.ApplicationContext;

public class Browser {

    public void launch()
    {
        final String port = System.getProperty("server.port");
        final String url = "localhost:"+ (port != null ? port : "8080");
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
                // TODO Auto-generated catch block
                e.printStackTrace();
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
                    e.printStackTrace();
                }
            }
            else {
                String cmd = OSValidator.isMac() ? "open " : "xdg-open ";
                try {
                    runtime.exec(cmd + url);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}