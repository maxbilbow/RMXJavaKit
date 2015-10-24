package click.rmx.engine.components;

/**
 * Created by Max on 03/10/2015.
 */
public interface UpdateableProperties {

    void setNeedsUpdate();

    /**
     *
     * @return
     */
    boolean needsUpdate();


    void updateProperties();


}
