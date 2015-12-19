package click.rmx.debug.logger;

import click.rmx.debug.logger.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import java.nio.charset.StandardCharsets;

//import click.rmx.debug.logger.config.WebConfig;

/**
 * Created by bilbowm on 23/10/2015.
 */
@SpringBootApplication
@EnableWebMvc
public class Application
//        extends WebMvcConfigurerAdapter
//        implements WebApplicationInitializer,
//        WebMvcConfigurer,
implements ServletContextListener
{


    public Application()
    {
//        onStartup();
    }



    private CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
        return characterEncodingFilter;
    }


    public static void main(String[] args)
    {
        SpringApplication.run(Application.class,args);
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();//getContext();

        context.register(WebConfig.class);

        final ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.addListener(new ContextLoaderListener(context));

        servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);

        //Add Mappings
        dispatcher.addMapping("*.html");
        dispatcher.addMapping("*.json");
        dispatcher.addMapping("*.xml");
        dispatcher.addMapping("*.js");
        dispatcher.addMapping("*.css");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {

    }
}