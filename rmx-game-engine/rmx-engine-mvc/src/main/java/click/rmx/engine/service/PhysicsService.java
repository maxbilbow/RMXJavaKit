package click.rmx.engine.service;

import click.rmx.engine.model.interfaces.GameObject;
import click.rmx.engine.model.interfaces.PhysicsBody;
import click.rmx.engine.model.interfaces.PhysicsWorld;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface PhysicsService {




    default void applyForceTo(PhysicsBody body, float[] force)
    {
        float[] velocity = body.getVelocity();
        float mass = body.getMass();
        //DO SOME MATHS
    }

    void checkConstraints(GameObject gameObject);

    /**
     * A database model can be used to apply these
     * @param body
     */
    default void updatePhysicsFor(PhysicsBody body, PhysicsWorld externalForces) {
//        if (externalForces != null) {
            float[] gravity = externalForces.getGravity();
            float[] weight = new float[gravity.length];
            float mass = body.getMass();
            for (int i=0;i<gravity.length;++i)
                weight[i] = gravity[i] * mass;
            applyForceTo(body, weight);
            checkConstraints(body.getGameObject());
//        }
//        if (body.getInternalPhysicsWorld() != null)
//            body.getGameObject()
//                    .getChildren()
//                    .stream()
//                    .forEach(o -> updatePhysicsFor(o.getPhysicsBody())
//                    );
    }






}
