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
    private float mass, friction, rotationalFriction, drag, rotationalDrag, restitution;
    private float[] scale, velocity;
    private boolean effectedByGravity;

    public PhysicsBodyImpl()
    {

    }

    public static PhysicsBody newInstance(GameObject gameObject)
    {
        PhysicsBodyImpl body = new PhysicsBodyImpl();
        body.gameObject = gameObject;
        body.collisionBody = CollisionBodyImpl.newInstance();
    }

    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public void setType(Type type) {

    }

    @Override
    public float setMass(float mass) {
        return 0;
    }

    @Override
    public GameObject setGameObject(GameObject gameObject) {
        return null;
    }

    @Override
    public CollisionBody getCollisionBody() {
        return null;
    }

    @Override
    public float getFriction() {
        return 0;
    }

    @Override
    public void setFriction(float friction) {

    }

    @Override
    public float getRollingFriction() {
        return 0;
    }

    @Override
    public void setRollingFriction(float rollingFriction) {

    }

    @Override
    public float getDamping() {
        return 0;
    }

    @Override
    public void setDamping(float damping) {

    }

    @Override
    public float getRotationalDamping() {
        return 0;
    }

    @Override
    public void setRotationalDamping(float rotationalDamping) {

    }

    @Override
    public float getRestitution() {
        return 0;
    }

    @Override
    public void setRestitution(float restitution) {

    }

    @Override
    public boolean isEffectedByGravity() {
        return false;
    }

    @Override
    public void setEffectedByGravity(boolean effectedByGravity) {

    }

    @Override
    public float[] getRotationalVelocity() {
        return new float[0];
    }

    @Override
    public float[] getVelocity() {
        return new float[0];
    }

    @Override
    public float getMass() {
        return 0;
    }

    @Override
    public float[] getScale() {
        return new float[0];
    }

    @Override
    public PhysicsWorld getInternalPhysicsWorld() {
        return null;
    }

    @Override
    public PhysicsWorld getExternalPhysicsWorld() {
        return null;
    }
}
