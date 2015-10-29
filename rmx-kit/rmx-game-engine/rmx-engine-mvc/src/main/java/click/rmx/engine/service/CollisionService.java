package click.rmx.engine.service;

import click.rmx.engine.model.interfaces.BoundingVolume;
import click.rmx.engine.model.interfaces.CollisionBody;


/**
 * Created by bilbowm on 28/10/2015.
 */
public interface CollisionService {


    boolean checkForCollision(CollisionBody bodyA, CollisionBody bodyB);


    void checkForStaticCollisions();

    void checkForDynamicCollisions();

    Integer getSecureKey();


    default boolean checkForCollision(BoundingVolume volumeA, BoundingVolume volumeB)
    {

    }
}
