package click.rmx.debug.logger;

import click.rmx.debug.logger.config.WebConfig;
import click.rmx.util.ObjectInspector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import java.nio.charset.StandardCharsets;

//import click.rmx.debug.logger.config.WebConfig;

/**
 * Created by bilbowm on 23/10/2015.
 */
//@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@WebListener
@Import(WebConfig.class)
public class Application implements WebApplicationInitializer
{


  public static String PORT;

  private CharacterEncodingFilter characterEncodingFilter()
  {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
    return characterEncodingFilter;
  }


  public static void main(String[] args)
  {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException
  {

    final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();//getContext();

    context.register(WebConfig.class);

    servletContext.addListener(new ContextLoaderListener(context)); //ContextLoadListener is here
    ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
    dispatcher.setLoadOnStartup(1);
    //

    //        ServletRegistration dispatcher = servletContext.getServletRegistration("dispatcherServlet");
    //Add Mappings
    dispatcher.addMapping("*.html");
    dispatcher.addMapping("*.json");
    dispatcher.addMapping("*.xml");
    dispatcher.addMapping("*.js");
    dispatcher.addMapping("*.css");


    //Filter mappings (is this needed?)
    //        servletContext.addFilter("characterEncodingFilter", characterEncodingFilter());
    PORT = context.getEnvironment().getProperty("httpPort");
    if (PORT == null)
      PORT = "8081";


  System.out.println(new ObjectInspector().stringify(System.getProperties()));
    //for webSockets
    dispatcher.setInitParameter("dispatchOptionsRequest", "true");
    dispatcher.setAsyncSupported(true);

  }
}