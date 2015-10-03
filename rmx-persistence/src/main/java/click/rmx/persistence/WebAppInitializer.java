package click.rmx.persistence;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext context = getContext();
		servletContext.addListener(new ContextLoaderListener(context)); //ContextLoadListener is here
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.html");
		dispatcher.addMapping("*.pdf");
		dispatcher.addMapping("*.json");
		dispatcher.addMapping("*.xml");
        dispatcher.addMapping("*.js");
		dispatcher.addMapping("*.css");

		setUpFilterMapping(servletContext);
		setUpSecurityMapping(servletContext);


	}

	private void setUpFilterMapping(ServletContext servletContext) {
		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("SpringOpenEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class);
		encodingFilter.setInitParameter("SpringOpenEntityManagerInViewFilter", "/*");
		encodingFilter.addMappingForUrlPatterns(null,false,"/*");
	}


	private void setUpSecurityMapping(ServletContext servletContext) {

		servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
				.addMappingForUrlPatterns(null, false, "/*");
//		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
//		encodingFilter.setInitParameter("springSecurityFilterChain", "/*");
//		encodingFilter.addMappingForUrlPatterns(null,false,"/*");
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocations("click.rmx.persistence.WebConfig", "click.rmx.persistence.SecurityConfig");

		return context;
	}



}
