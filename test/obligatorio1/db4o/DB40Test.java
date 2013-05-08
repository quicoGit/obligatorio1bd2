package obligatorio1.db4o;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import obligatorio1.db4o.modelo.Auto;
import obligatorio1.db4o.modelo.Persona;
import obligatorio1.db4o.modelo.Vehiculo;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 *
 * @author tomas
 */
public class DB40Test {

    public DB40Test() {
    }
    static AltasYBajas altasybajas;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        altasybajas = new AltasYBajas("tests.dbo");
    }

    @AfterClass
    public static void tearDownClass() {
        altasybajas.getDb().close();
        new File("tests.dbo").delete();
    }

    @Before
    public void setUp() {
        for (Object object : altasybajas.getDb().queryByExample(null)) {
            altasybajas.getDb().delete(object);
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAgregarPersona() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = Arrays.asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);

        assertEquals(altasybajas.findPersona(45203730), p);
        assertEquals(altasybajas.findPersona(45203730).getVehiculos().toArray(new Vehiculo[]{})[0], fiatUno);
        assertEquals(altasybajas.findPersona(45203730).getVehiculos().toArray(new Vehiculo[]{})[0].getDueño(), p);
    }

    @Test
    public void testAgregarPersonaDuplicada() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = Arrays.asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);
        exception.expect(RuntimeException.class);
        altasybajas.altaDePersona(p, null, null);

        assertEquals(altasybajas.findPersona(45203730), p);
    }

    @Test
    public void testAgregarPersonaMismaCedula() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = Arrays.asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);

        exception.expect(RuntimeException.class);
        altasybajas.altaDePersona(new Persona(45203730, "John", "House"), null, null);

        assertEquals(altasybajas.findPersona(45203730), p);
    }

    @Test
    public void testBorrarPersona() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = Arrays.asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);
        assertEquals(altasybajas.findPersona(45203730), p);

        altasybajas.bajaDePersona(p);
        assertEquals(altasybajas.findPersona(45203730), null);

        altasybajas.altaDePersona(p, vehiculos, null);
        assertEquals(altasybajas.findPersona(45203730), p);

        altasybajas.bajaDePersona(p);
        assertEquals(altasybajas.findPersona(45203730), null);
    }

    @Test
    public void testBorrarPersonaInexistente() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        exception.expect(RuntimeException.class);
        altasybajas.bajaDePersona(p);
        altasybajas.bajaDePersona(p);
    }

    @Test
    public void testConsultas() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = Arrays.asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);

        assertEquals(Consultas.consultaPromedio(altasybajas.getDb()), 1, 0.01);
    }
}