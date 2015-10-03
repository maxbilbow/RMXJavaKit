package click.rmx.persistence.model;

/**
 * Created by Max on 03/10/2015.
 */
public interface RMXPersistence {

    Long getId();
    void setId(Long id);

    default boolean equals(RMXPersistence object) {
        if (object != null && object.getId() == this.getId())
            return true;
        return false;
    }


    default boolean onSave() {
        //TODO
        return true;
    }


}

