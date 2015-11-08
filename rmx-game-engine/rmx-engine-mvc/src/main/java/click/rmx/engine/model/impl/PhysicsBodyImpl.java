package click.rmx.engine.model.impl;

import click.rmx.engine.model.interfaces.CollisionBody;
import click.rmx.engine.model.interfaces.GameObject;
import click.rmx.engine.model.interfaces.PhysicsBody;
import click.rmx.engine.model.interfaces.PhysicsWorld;

import javax.persistence.Entity;

/**
 * Created by bilbowm on 28/10/2015.
 */
@Entity(name = "PhysicsBodies")
public class PhysicsBodyImpl implements PhysicsBody{

    private GameObject gameObject;
    private CollisionBody collisionBody;
    private float mass, friction, rollingFriction, drag, rotationalDrag, restitution, damping, rotationalDamping;
    private float[] scale, velocity, rotationalVelocity;
    private boolean effectedByGravity;
    private PhysicsBody.Type type;
    private PhysicsWorld internalPhysicsWorld, externalPhysicsWorld;

    public PhysicsBodyImpl()
    {

    }

    public static PhysicsBody newInstance(GameObject gameObject)
    {
        PhysicsBodyImpl body = new PhysicsBodyImpl();
        body.gameObject = gameObject;
        body.collisionBody = CollisionBodyImpl.newInstance(body);
        return body;
    }

    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void setMass(float mass) {
        this.mass = mass;
    }

    @Override
    public CollisionBody getCollisionBody() {
        return this.collisionBody;
    }

    @Override
    public float getFriction() {
        return this.friction;
    }

    @Override
    public void setFriction(float friction) {

    }

    @Override
    public float getRollingFriction() {
        return this.rollingFriction;
    }

    @Override
    public void setRollingFriction(float rollingFriction) {
        this.rollingFriction = rollingFriction;
    }

    @Override
    public float getDamping() {
        return this.damping;
    }

    @Override
    public void setDamping(float damping) {
        this.damping = damping;
    }

    @Override
    public float getRotationalDamping() {
        return 0;
    }

    @Override
    public void setRotationalDamping(float rotationalDamping) {
        this.rotationalDamping = rotationalDamping;
    }

    @Override
    public float getRestitution() {
        return restitution;
    }

    @Override
    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    @Override
    public boolean isEffectedByGravity() {
        return effectedByGravity;
    }

    @Override
    public void setEffectedByGravity(boolean effectedByGravity) {
        this.effectedByGravity = effectedByGravity;
    }

    @Override
    public float[] getRotationalVelocity() {
        return rotationalVelocity;
    }

    @Override
    public float[] getVelocity() {
        return velocity;
    }

    @Override
    public float getMass() {
        return mass;
    }

}
