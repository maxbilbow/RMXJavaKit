package click.rmx.persistence.controller;

import click.rmx.persistence.repository.ExtendedBoxRepository;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by Max on 12/10/2015.
 */
@Controller
public class ExtendedBoxController {

    @Resource
    private ExtendedBoxRepository extendedBoxRepository;

    private static ExtendedBoxController ourInstance;

    public static ExtendedBoxController getInstance()
    {
        return ourInstance != null ? ourInstance : new ExtendedBoxController();
    }

    public ExtendedBoxController()
    {
        ourInstance = this;
    }

    public ExtendedBoxRepository getExtendedBoxRepository() {
        return extendedBoxRepository;
    }


}
