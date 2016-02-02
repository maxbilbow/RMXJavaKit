package main.java.uk.co.utilisoft.poi;

import click.rmx.web.Browser;
import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PoisxlsApplication {

	public static void main(String[] args) {
		System.setProperty("timestamp",new DateTime().toString("Y-M-d_Hms"));
		ConfigurableApplicationContext context = SpringApplication.run(PoisxlsApplication.class, args);
		System.setProperty("server.port",context.getEnvironment().getProperty("server.port"));
		new Browser().launch();
	}
}
