package click.rmx.engine.model.interfaces;

import click.rmx.engine.model.impl.PhysicsBodyImpl;

import java.util.List;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface GameObject {
    float [] getPosition();

    float [] getOrientation();

    GameObject getParent();

    List<GameObject> getChildren();

    List<GameComponent> getComponents();

    List<Behaviour> getBehaviours();

    PhysicsBody getPhysicsBody();

    /**
     * Creates and adds a default PhysicsBody.
     * @return
     */
    default PhysicsBody addPhysicsBody() {
        if (getPhysicsBody() == null) {
            PhysicsBody body = PhysicsBodyImpl.newInstance(this);
            this.setPhysicsBody(body);
            return body;
        } else {
            return this.getPhysicsBody();
        }
    }

    void setPhysicsBody(PhysicsBody body);

    String getGeometryName();

    float[] getScale();

    /**
     * NOTE: The root node must not have a PhysicsWorld object...?
     * @return the physics to be applied to the physicsBody of this node
     */
    PhysicsWorld getPhysicsWorld();



}
