package click.rmx.testsuite;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(FoundationTest.class)
@ExcludeCategory(ExcludeCat.class)
@SuiteClasses({ 

	})
public class RMXKitTests {

}
