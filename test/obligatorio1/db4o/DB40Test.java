package obligatorio1.db4o;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.db4o.Db4oEmbedded;

/**
 *
 * @author tomas
 */
public class DB40Test {

    public DB40Test() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Db4oEmbedded.openFile("test.dbo");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class DB40.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        DB40.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}