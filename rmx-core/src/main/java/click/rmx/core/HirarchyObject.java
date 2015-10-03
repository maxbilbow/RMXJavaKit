package click.rmx.core;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by Max on 03/10/2015.
 */
public interface HirarchyObject {
    Object getParent();
    List getChildren();
    boolean isRoot();

    void setParent(Object object);

    default boolean removeChild(Object child) {
        if (!this.getChildren().contains(child))
            return false;
        else
            this.getChildren().remove(child);
        return true;
    }

}
