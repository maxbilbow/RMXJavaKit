/**
 * 
 */
package click.rmx.engine.physics;

import click.rmx.engine.actors.EntityGenerator;
import org.junit.Before;
import org.junit.Test;



import click.rmx.engine.components.Node;
import click.rmx.engine.components.Nodes;
import click.rmx.engine.Scene;

/**
 * @author Max
 *
 */
public class PhysicsWorldTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		EntityGenerator eg = new EntityGenerator() {

			@Override
			public Node makeEntity() {
				Node n = Nodes.newGameNode();
				n.setPhysicsBody(PhysicsBody.newDynamicBody());
				return n;
			}
			
		};
		Scene s = new Scene();
		eg.makeShapesAndAddToScene(s, 10);
		this.rootNode = s.rootNode();
		
	}
	Node rootNode = Nodes.newRootNode();
	@Test
	public void test() {
		PhysicsWorld physics = new PhysicsWorld();
		physics.updateCollisionEvents(rootNode);
		System.out.println(physics.count);
	}

}
