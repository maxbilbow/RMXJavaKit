package com.maxbilbow.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Max on 19/12/2015.
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String get()
    {
        return "Hello World";
    }

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

}
