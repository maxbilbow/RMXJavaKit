package click.rmx.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Max on 12/10/2015.
 */
@Entity
public class ExtendedBox {//extends Box {

    @Id
    @GeneratedValue
    private Long id;

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString() + ", VALUE: " + value;
    }

//    @Override
    public Long getId() {
        return id;
    }

//    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
