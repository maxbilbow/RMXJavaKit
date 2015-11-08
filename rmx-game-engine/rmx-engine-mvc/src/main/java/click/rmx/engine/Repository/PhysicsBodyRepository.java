package click.rmx.engine.Repository;

import click.rmx.engine.model.interfaces.PhysicsBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bilbowm on 28/10/2015.
 */
@Repository
public interface PhysicsBodyRepository extends JpaRepository<PhysicsBody, Long> {


    List<PhysicsBody> getStaticBodies();

    List<PhysicsBody> getDynamicBodies();

    List<PhysicsBody> getKinematicBodies();
}
