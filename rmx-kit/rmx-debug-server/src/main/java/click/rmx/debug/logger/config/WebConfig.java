package click.rmx.debug.logger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import java.util.Locale;

/**
 * Created by bilbowm on 23/10/2015.
 */
@Configuration
//@EnableWebMvc
@Import({
        DBConfig.class//, EndpointConfig.class
})
@WebListener
@ComponentScan(basePackages = "click.rmx.debug.logger")
public class WebConfig
        extends WebMvcConfigurerAdapter
        implements WebApplicationInitializer
{

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }


    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");



        return resolver;
    }

    /**
     * Setup a simple strategy: use all the defaults and return XML by default when not sure.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
//        configurer.mediaType("xml", MediaType.APPLICATION_XML);
//        configurer.mediaType("txt", MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/css/**").addResourceLocations("/assets/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/assets/js/");
    }

    @Override
//    @Autowired
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

        //for webSockets
        dispatcher.setInitParameter("dispatchOptionsRequest", "true");
        dispatcher.setAsyncSupported(true);

    }
}
