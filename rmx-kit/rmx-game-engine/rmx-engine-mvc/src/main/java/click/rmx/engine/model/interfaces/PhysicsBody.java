package click.rmx.engine.model.interfaces;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface PhysicsBody {
    GameObject getGameObject();

    Type getType();

    void setType(Type type);

    float setMass(float mass);

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

//    void setRotationalVelocity(float[] rotationalVelocity);

    float[] getVelocity();

//    void setVelocity(float[] velocity);

    float getMass();

    float[] getScale();

//    void setScale(float[] scale);

    enum Type {
        Static, Dynamic, Kinematic
    }


    /**
     * NOTE: The root not must have a PhysicsWorld object. It is not necessary for its children.
     * @return the physics to be applied to the children of this node or null
     */
    PhysicsWorld getInternalPhysicsWorld();

    /**
     *
     * @return the physics world acting on this body.
     */
    PhysicsWorld getExternalPhysicsWorld();

    class DB {
        public static final String TABLE_NAME = "PhysicsBodies";
    }
}
