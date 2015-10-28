package click.rmx.engine.Repository;

import click.rmx.engine.model.interfaces.CollisionBody;

import java.util.List;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface CollisionBodyRepository {

    List<CollisionBody> getStaticBodies();

    List<CollisionBody> getDynamicBodies();

    List<CollisionBody> getKinematicBodies();
}
