package click.rmx.persistence.service;

import org.springframework.stereotype.Service;

/**
 * Created by Max on 03/10/2015.
 */
@Service
public class TransformService {
    private static TransformService ourInstance;

    public static TransformService getInstance() {
        return ourInstance != null ? ourInstance : new TransformService();
    }

    public TransformService() {
        ourInstance = this;
    }
}
