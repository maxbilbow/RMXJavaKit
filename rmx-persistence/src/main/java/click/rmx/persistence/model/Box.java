package click.rmx.persistence.model;

import click.rmx.core.RMXObject;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Max on 09/10/2015.
 */
@Entity
public class Box extends RMXObject {

    @Id
    @GeneratedValue
    private Long id;


    private String name;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        super.setName(this.name = name);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + ", name: " + this.name;
    }
}
