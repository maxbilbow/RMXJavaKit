package click.rmx.core;

import java.util.List;

/**
 * Created by Max on 03/10/2015.
 */
public interface HierarchyObject {
    HierarchyObject getParent();
    List getChildren();

    default boolean isRoot() {
        return this.getParent() == null;
    }


    default boolean removeChild(Object child) {
        if (!this.getChildren().contains(child))
            return false;
        else
            this.getChildren().remove(child);
        return true;
    }

    default boolean removeChild(HierarchyObject child) {
        return this.removeChild((Object) child);
    }

    void setParent(HierarchyObject parent);



}
