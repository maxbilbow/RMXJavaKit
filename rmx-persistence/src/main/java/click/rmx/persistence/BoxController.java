package click.rmx.persistence;

import click.rmx.debug.Bugger;
import click.rmx.persistence.model.Box;
import click.rmx.persistence.repository.BoxRepository;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Max on 09/10/2015.
 */
@Controller
public class BoxController {

    @Resource
    private BoxRepository boxRepository;

    public BoxController()
    {
        if (instance == null)
        {
            instance = this;
        }
        else
        {
            System.exit(-1);
        }
    }
    private static BoxController instance = null;
    public static BoxController getInstance()
    {
        return instance != null ? instance : newInstance();
    }

    private static BoxController newInstance() {
        Bugger.logAndPrint("Creating new instance", false);
        return instance = new BoxController();
    }

    public void save(Box box) {
        boxRepository.save(box);
    }

    public List<Box> getAll()
    {
        return boxRepository.findAll();
    }
}
