import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({PlayerTest.class, CardTest.class, PackTest.class, CardDeckTest.class})
public class TestSuite{}