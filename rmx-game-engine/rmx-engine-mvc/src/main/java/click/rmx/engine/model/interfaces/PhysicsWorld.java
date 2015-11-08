package click.rmx.engine.model.interfaces;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface PhysicsWorld {
    float[] getGravity();

    static PhysicsWorld defaultPhysicsWorld()
    {
        return new DefaultPhysicsWorld(){};
    }

    abstract class DefaultPhysicsWorld implements PhysicsWorld {

        private final float [] gravity = { 0, -9.8f, 0};
        @Override
        public float[] getGravity() {
            return gravity;
        }
    }
}
