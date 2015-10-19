package click.rmx.persistence;

import click.rmx.debug.Bugger;
import click.rmx.debug.Tests;
import click.rmx.persistence.controller.ExtendedBoxController;
import click.rmx.persistence.model.Box;
import click.rmx.persistence.model.ExtendedBox;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

//import org.junit.BeforeClass;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RmxSpringApplication.class)
public class RmxSpringApplicationTests {

	private static Long noOfBoxes = null;
    private static final SQLType sqlType = SQLType.MY_SQL;

    static {
        SQLConnectionDescriber sqlDescriber;// = SQLConnectionDescriber.newMySQLDescriber();


        switch (sqlType) {
            case HSQLDB:
                sqlDescriber = SQLConnectionDescriber.newHSQLDBDescriber();
                String file = RmxSpringApplication.class.getResource("").getFile() + "hsqldb/rmxdb";
                Bugger.logAndPrint(file, true);
                sqlDescriber.setConnectionUrl("jdbc:hsqldb:file:" + file +
                        "?autoReconnect=true&createDatabaseIfNotExist=true");
                break;
            case MY_SQL:
            default:
                sqlDescriber = SQLConnectionDescriber.newMySQLDescriber();
                sqlDescriber.setConnectionUrl("jdbc:mysql://localhost:3306/rmxdb" +
                        "?autoReconnect=true&createDatabaseIfNotExist=true");
                break;

        }

        sqlDescriber.setPackagesToScan("click.rmx.persistence.model");

        RmxSpringApplication.startDatabaseWithProperties(null, sqlDescriber);
    }
	@BeforeProcess
	public static void setupBeforeClass()
	{



    }

	@Before
	public void setInitialCount()
	{
		if (noOfBoxes == null)
			noOfBoxes = BoxController.getInstance().getBoxRepository().count();
	}

	@AfterClass
	public static void tearDown()
	{
        BoxController.getInstance().forEach(Tests::note);
	}

   // @Test
    public void addBoxToDatabase()
    {
        Box box = new Box();
        box.setName("Boxy: " + Math.round(Math.random() * 10));
        BoxController bc = BoxController.getInstance();
        bc.save(box);
        bc.getAll().stream().forEach(System.out::println);

        assertTrue("Box count has changed",noOfBoxes != bc.getBoxRepository().count());
        assertTrue("One box was added to the database", noOfBoxes == bc.getBoxRepository().count() - 1);
        noOfBoxes = bc.getBoxRepository().count();
    }

    @Test
    @Ignore
    public void removeBoxTest()
    {
        // setup
        BoxController bc = BoxController.getInstance();
        List<Box> boxes = bc.getBoxRepository().findAll();
        Long id = boxes.get(0).getId(); //TODO account for empty db
        Tests.note("About to remove box with id: " + id);

        // execute
        bc.getBoxRepository().delete(id);

        // verify
        assertTrue("Box count should have changed", noOfBoxes != bc.getBoxRepository().count());
        assertTrue("One box was was deleted from the database", noOfBoxes == bc.getBoxRepository().count() + 1);
        assertTrue("Box with ID: " + id + " no longer exists in DB",!bc.getBoxRepository().exists(id));
        noOfBoxes = bc.getBoxRepository().count();
    }

	@Test
	public void contextLoads()
	{
		assertTrue(BoxController.getInstance() != null);
	}

    @Test
    public void createExtendedBox()
    {
        ExtendedBoxController ebc = ExtendedBoxController.getInstance();

        ExtendedBox eBox = new ExtendedBox();
        eBox.setValue(4);

        ebc.getExtendedBoxRepository().save(eBox);

        assertTrue(ebc.getExtendedBoxRepository().count() > 0);
        ebc.getExtendedBoxRepository().findAll().stream().forEach(Tests::note);
    }

}
