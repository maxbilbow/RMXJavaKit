package click.rmx.debug.server;

import click.rmx.debug.server.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.nio.charset.StandardCharsets;

/**
 * Created by bilbowm on 23/10/2015.
 */
//@SpringBootApplication
public class Application implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context)); //ContextLoadListener is here
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);

        addMappings(dispatcher);

        setUpFilterMapping(servletContext);

        //for webSockets
        dispatcher.setInitParameter("dispatchOptionsRequest", "true");
        dispatcher.setAsyncSupported(true);


    }

    private void addMappings(ServletRegistration.Dynamic dispatcher) {
        dispatcher.addMapping("*.html");

        dispatcher.addMapping("*.json");
        dispatcher.addMapping("*.xml");
        dispatcher.addMapping("*.js");
        dispatcher.addMapping("*.css");
    }

    private void setUpFilterMapping(ServletContext servletContext) {
//        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("SpringOpenEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class);
//        encodingFilter.setInitParameter("SpringOpenEntityManagerInViewFilter", "/*");
//        encodingFilter.addMappingForUrlPatterns(null, false, "/*");

        //For Security
//        servletContext.addFilter("securityFilter",
//                new DelegatingFilterProxy("springSecurityFilterChain"))
//                .addMappingForUrlPatterns(null, false, "/*");

        //For Sockets?
        servletContext.addFilter("characterEncodingFilter", characterEncodingFilter());
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        return context;
    }


    //TODO
    private CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
        return characterEncodingFilter;
    }
}