package click.rmx.engine.components;


import click.rmx.engine.GameController;
import click.rmx.engine.Scene;
import click.rmx.engine.geometry.Shapes;
import click.rmx.engine.physics.PhysicsBody;
import click.rmx.persistence.model.PersistenceTransform;
import click.rmx.persistence.model.Transform;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.annotations.BeforeMethod;

import static org.junit.Assert.assertTrue;

/**
 * Created by Max on 07/10/2015.
 */
public class GameNodeTest {

    private Node node = null;

    @BeforeClass
    public static void setUpBeforeClass()
    {
        System.out.println("Setting up before class");
        new GameController() {
            @Override
            protected void initpov() {

            }

            @Override
            public void setup() {

            }

            @Override
            public void updateBeforeSceneRender(Object... args) {

            }
        };

        assertTrue(Scene.getCurrent() != null);

        assertTrue(Scene.getCurrent().rootNode() != null);
    }

    @Before
    public void setUp() throws Exception {
        node = Nodes.defaultAiNode();
        node.addToCurrentScene();

        assertTrue(node.getParent() != null);
        assertTrue("Parent should have transform", node.getParent().getTransform() != null);
        assertTrue("Parent should be rootNode", node.getParent().getTransform() == Scene.getCurrent().rootNode().getTransform());
    }

    @After
    public void tearDown() throws Exception {
        node.removeFromParent();
        node = null;
    }

    @Test
    public void testComponentsShareTransform()
    {
        PersistenceTransform transform = node.getTransform();
        node.getComponents().forEach(nodeComponent -> {
            assertTrue(nodeComponent.transform() != null && nodeComponent.transform() == transform);
        });
    }

    @Test
    public void testComponentsShareNode()
    {
        node.getComponents().forEach(nodeComponent -> {
            assertTrue(nodeComponent.getNode() != null && nodeComponent.getNode() == node);
        });
    }

    @Test
    public void testGetComponents() throws Exception {


    }

    @Test
    public void testGetComponent() throws Exception {

    }

    @Test
    public void testRemoveChildNode() throws Exception {

    }

    @Test
    public void testGetChildWithName() throws Exception {

    }

    @Test
    public void testGetTransform() throws Exception {
        assertTrue(node.transform() == node.getTransform());
    }
}