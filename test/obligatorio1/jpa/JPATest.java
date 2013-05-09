package obligatorio1.jpa;

import obligatorio1.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 *
 * @author tomas
 */
public class JPATest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    static AltasYBajas altasybajas;

    @BeforeClass
    public static void setUpClass() {
        // Antes de empezar ningun test: ej crear base de datos, tablas, etc
    }

    @AfterClass
    public static void tearDownClass() {
        // Despuéss terminar de correr todos los tests, borrar base de datos, tablas
    }

    @Before
    public void setUp() {
        // Cada vez justo antes de empezar un test particular. ej: borrar filas de las tablas
    }

    @After
    public void tearDown() {
        // Cada vez justo después de completar un test particular. No es obligatorio
    }

    @Test
    public void testAgregarPersona() throws Exception {
    }

    @Test
    public void testAgregarPersonaDuplicada() throws Exception {
    }

    @Test
    public void testAgregarPersonaMismaCedula() throws Exception {
    }

    @Test
    public void testBorrarPersona() throws Exception {
    }

    @Test
    public void testBorrarPersonaInexistente() throws Exception {
    }

    @Test
    public void testConsultas() throws Exception {
    }
}