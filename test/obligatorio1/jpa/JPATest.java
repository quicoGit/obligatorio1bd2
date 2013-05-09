package obligatorio1.jpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.LockModeType;
import obligatorio1.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import org.junit.rules.ExpectedException;

/**
 *
 * @author tomas
 */
public class JPATest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    static ActualizadorBDYConsultasJPQL actualizadorBDYConsultasJPQL;

    @BeforeClass
    public static void setUpClass() {
        actualizadorBDYConsultasJPQL = new ActualizadorBDYConsultasJPQL();
    }

    @AfterClass
    public static void tearDownClass() {
        if (actualizadorBDYConsultasJPQL.getEm() != null) {
            actualizadorBDYConsultasJPQL.getEm().close();
            actualizadorBDYConsultasJPQL.setEm(null);
        }
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
        Persona p = new Persona(45609876, "Jeasmine", "Bv Artigas");
        LicenciaConductor li = new LicenciaConductor(actualizadorBDYConsultasJPQL.verificarConexion(), 23178938, "R", null, 45609876, null, 1);
        p.agregarLicencia(li);
        Auto v1 = new Auto(actualizadorBDYConsultasJPQL.verificarConexion(), true, "aaa000", "1234567", "ABCD98765", "BMW", "2012", 45609876);
        p.agregarVehiculo(v1);
        actualizadorBDYConsultasJPQL.altaDePersona(p, (List) p.getVehiculos(), (List) p.getLicenciasDeConducir());
        assertEquals(actualizadorBDYConsultasJPQL.verificarConexion().find(Persona.class, 45609876), p);
        assertEquals(actualizadorBDYConsultasJPQL.verificarConexion().find(Vehiculo.class, "aaa000").getDueño(), p);
        assertEquals(actualizadorBDYConsultasJPQL.verificarConexion().find(LicenciaConductor.class, 23178938).getPropietario(), p);
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