package click.rmx.debug.logger;

import click.rmx.debug.logger.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.nio.charset.StandardCharsets;

/**
 * Created by bilbowm on 23/10/2015.
 */
@SpringBootApplication

//@EnableAutoConfiguration
//    @Configuration
public class Application extends WebMvcConfigurerAdapter implements WebApplicationInitializer {



    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();//getContext();

        context.register(WebConfig.class);

        servletContext.addListener(new ContextLoaderListener(context)); //ContextLoadListener is here
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);


        //Add Mappings
        dispatcher.addMapping("*.html");
        dispatcher.addMapping("*.json");
        dispatcher.addMapping("*.xml");
        dispatcher.addMapping("*.js");
        dispatcher.addMapping("*.css");

        //Filter mappings (is this needed?)
        servletContext.addFilter("characterEncodingFilter", characterEncodingFilter());

        //for webSockets
        dispatcher.setInitParameter("dispatchOptionsRequest", "true");
        dispatcher.setAsyncSupported(true);

    }

    private CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
        return characterEncodingFilter;
    }

    /**
     * Setup a simple strategy: use all the defaults and return XML by default when not sure.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("xml", MediaType.APPLICATION_XML);
//        configurer.mediaType("txt", MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/css/**").addResourceLocations("/assets/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/assets/js/");
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class,args);
    }
}