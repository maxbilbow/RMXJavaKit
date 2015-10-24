package click.rmx.persistence.model;

import click.rmx.core.RMXObject;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Max on 03/10/2015.
 */

public abstract class RMXPersistenceObject extends RMXObject implements RMXPersistence {

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


}
