package click.rmx.debug.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

/**
 * Created by bilbowm on 23/10/2015.
 */
@Configuration
@EnableWebMvc
@Import({
        DBConfig.class//, EndpointConfig.class
})
@ComponentScan(basePackages = "click.rmx.debug.server")
public class WebConfig extends WebMvcConfigurerAdapter {

//
//    //Normal setup
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages");
//        return messageSource;
//    }



    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
        changeInterceptor.setParamName("language");
        registry.addInterceptor(changeInterceptor);
        //Websockets
//        registry.addInterceptor(webContentInterceptor());
    }

    @Override //Websockets
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//     //Websockets
//    private WebContentInterceptor webContentInterceptor() {
//        WebContentInterceptor interceptor = new WebContentInterceptor();
//        interceptor.setCacheSeconds(0);
//        interceptor.setUseExpiresHeader(true);
//        interceptor.setUseCacheControlHeader(true);
//        interceptor.setUseCacheControlNoStore(true);
//        return interceptor;
//    }
    /**
     * Setup a simple strategy: use all the defaults and return XML by default when not sure.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");


        return resolver;
    }



    //configure freemarker
//    @Bean
//    public FreeMarkerConfigurer freemarkerConfig() throws Exception
//    {
//        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//
//        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/pages/");
//
//
//        return freeMarkerConfigurer;
//    }
//
//    @Bean
//    public FreeMarkerViewResolver viewResolver() {
//        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
//        freeMarkerViewResolver.setCache(true);
////        freeMarkerViewResolver
//        freeMarkerViewResolver.setPrefix("");
//        freeMarkerViewResolver.setSuffix(".ftl");
//
//
//        return freeMarkerViewResolver;
//    }

        @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/css/**").addResourceLocations("/assets/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/assets/js/");
    }

}
