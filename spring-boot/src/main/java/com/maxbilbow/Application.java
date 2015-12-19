package com.maxbilbow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class Application {

//    @Bean
//    public Browser browser()
//    {
//        return new Browser();
//    }

    @RequestMapping("/string")
    public String getString()
    {
        return "Hello, World!";
    }

    @RequestMapping("/ftl")
    public ModelAndView getFtl()
    {
        return new ModelAndView("index");
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
