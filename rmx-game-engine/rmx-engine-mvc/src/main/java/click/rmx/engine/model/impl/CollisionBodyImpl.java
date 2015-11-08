package click.rmx.engine.model.impl;

import click.rmx.engine.model.interfaces.CollisionBody;
import click.rmx.engine.model.interfaces.PhysicsBody;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by bilbowm on 28/10/2015.
 */
@Entity(name = "CollisonBodies")
public class CollisionBodyImpl implements CollisionBody {


    @OneToOne
    private PhysicsBody physicsBody;

    private int collisionGroup;

    static CollisionBody newInstance(PhysicsBody physicsBody) {
        CollisionBodyImpl body = new CollisionBodyImpl();
        body.physicsBody = physicsBody;
        body.collisionGroup = Group.DEFAULT;
        //TODO: Setup
        return body;
    }

    @Override
    public int getCollisionGroup() {
        return collisionGroup;
    }

    public void setCollisionGroup(int collisionGroup) {
        this.collisionGroup = collisionGroup;
    }
}
