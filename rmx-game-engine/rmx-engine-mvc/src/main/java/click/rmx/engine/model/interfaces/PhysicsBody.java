package click.rmx.engine.model.interfaces;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface PhysicsBody {
    GameObject getGameObject();

    Type getType();

    void setType(Type type);

    void setMass(float mass);

//    GameObject setGameObject(GameObject gameObject);

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


//    void setScale(float[] scale);

    enum Type {
        Static, Dynamic, Kinematic
    }

    class DB {
        public static final String TABLE_NAME = "PhysicsBodies";
    }
}
