package click.rmx.engine.model;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface PhysicsBody {
    enum Type {
        Static, Dynamic, Kinematic
    }
    GameObject getGameObject();

    Type getType();

    void setVelocity(float[] velocity);

    float setMass(float mass);

    void setScale(float[] scale);

    void setType(Type type);

    GameObject setGameObject(GameObject gameObject);

    CollisionBody getCollisionBody();

    float getFriction();

    void setFriction(float friction);

    float getRollingFriction();

    void setRollingFriction(float rollingFriction);

    float getDamping();

    void setDamping(float damping);

    float getRotationalDamping();

    void setRotationalDamping(float rotationalDamping);

    float getRestitution();

    void setRestitution(float restitution);

    boolean isEffectedByGravity();

    void setEffectedByGravity(boolean effectedByGravity);

    float[] getRotationalVelocity();

    void setRotationalVelocity(float[] rotationalVelocity);

    float[] getVelocity();

    float getMass();

    float[] getScale();



}
