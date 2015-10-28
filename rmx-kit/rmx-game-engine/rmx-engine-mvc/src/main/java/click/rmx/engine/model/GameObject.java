package click.rmx.engine.model;

import java.util.List;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface GameObject {
    float [] getPosition();

    float [] getOrientation();

    List<GameComponent> getComponents();

    List<Behaviour> getBehaviours();

    PhysicsBody getPhysicsBody();

    Geometry getGeometry();

}
