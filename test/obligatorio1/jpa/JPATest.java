package obligatorio1.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    static CreadorDeObjetos c;
    Date dNow = new Date();

    @BeforeClass
    public static void setUpClass() {
        actualizadorBDYConsultasJPQL = new ActualizadorBDYConsultasJPQL();
        c = new CreadorDeObjetos();
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

    public void limpiarBD() {
        Query personas = actualizadorBDYConsultasJPQL.verificarConexion().createQuery("Select persona from Persona persona");
        List<Persona> idsPersona = personas.getResultList();
        for (Persona persona : idsPersona) {
            actualizadorBDYConsultasJPQL.bajaDePersona(persona.getCi());
        }
        Query vehiculos = actualizadorBDYConsultasJPQL.verificarConexion().createQuery("Select vehiculo from Vehiculo vehiculo");
        List<Vehiculo> idsVehiculo = vehiculos.getResultList();
        for (Vehiculo vehiculo : idsVehiculo) {
            actualizadorBDYConsultasJPQL.bajaDeVehiculo(vehiculo.getMatricula());
        }
        Query tipomoto = actualizadorBDYConsultasJPQL.verificarConexion().createQuery("Select tipomoto from TipoMoto tipomoto");
        List<TipoMoto> idsTipoMoto = tipomoto.getResultList();
        for (TipoMoto tipoMoto : idsTipoMoto) {
            actualizadorBDYConsultasJPQL.eliminarTipoMoto(tipoMoto.getId());
        }
        Query departamentosID = actualizadorBDYConsultasJPQL.verificarConexion().createQuery("Select departamento from Departamento departamento");
        List<Departamento> ids = departamentosID.getResultList();
        for (Departamento departamento : ids) {
            actualizadorBDYConsultasJPQL.eliminarDepartamento(departamento.getId());
        }
    }

    @Test
    public void testAgregarPersona() throws Exception {
        limpiarBD();
        Persona p = new Persona(45607876, "Jeasmine", "Bv Artigas");
        Departamento dep = new Departamento(1, "Montevideo");
        c.crearDepartamento(actualizadorBDYConsultasJPQL.verificarConexion(), dep);
        LicenciaConductor li = new LicenciaConductor(23178758, "R", dNow, p, "Sin restriccion", dep);
        List<LicenciaConductor> licenciasDeConducir = new ArrayList<>();
        licenciasDeConducir.add(li);
        Auto v1 = new Auto(true, "jjj000", "1234567", "ABCD98765", "BMW", "2012", p);
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(v1);
        actualizadorBDYConsultasJPQL.altaDePersona(p, vehiculos, licenciasDeConducir);
        assertEquals(p, actualizadorBDYConsultasJPQL.verificarConexion().find(Persona.class, 45607876));
        assertEquals(p, actualizadorBDYConsultasJPQL.verificarConexion().find(Vehiculo.class, "jjj000").getDueño());
        assertEquals(p, actualizadorBDYConsultasJPQL.verificarConexion().find(LicenciaConductor.class, 23178758).getPropietario());
    }

    public List<Vehiculo> agregarVehiculos() {
        Persona p = new Persona(49757876, "Thomas", "Rivera");
        actualizadorBDYConsultasJPQL.altaDePersona(p, null, null);
        Vehiculo v2 = new Vehiculo("xxx000", "1234567", "ABCD98765", "MiniCooper", "2012", p);
        TipoMoto tipo = new TipoMoto(2, "De calle");
        c.crearTipoMoto(actualizadorBDYConsultasJPQL.verificarConexion(), tipo);
        Moto v4 = new Moto(tipo, "zzz000", "35347547", "GFJGFH45435", "Chevrolet", "2004", p);
        Camion v3 = new Camion(140, (short) 10, "lll000", "35457447", "JLMGFH45435", "Chevrolet", "2006", p);
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(v3);
        vehiculos.add(v2);
        vehiculos.add(v4);
        for (Vehiculo vehiculo : vehiculos) {
            actualizadorBDYConsultasJPQL.altaVehiculo(vehiculo);
        }
        return vehiculos;
    }

    @Test
    public void testBajaVehiculo() throws Exception {
        List<Vehiculo> vehiculos = agregarVehiculos();
        for (Vehiculo vehiculo : vehiculos) {
            assertEquals(vehiculo, actualizadorBDYConsultasJPQL.verificarConexion().find(Vehiculo.class, vehiculo.getMatricula()));
            actualizadorBDYConsultasJPQL.bajaDeVehiculo(vehiculo.getMatricula());
        }
        for (Vehiculo vehiculo : vehiculos) {
            assertEquals(null, actualizadorBDYConsultasJPQL.verificarConexion().find(Vehiculo.class, vehiculo.getMatricula()));
        }
    }

    @Test
    public void testActualizarLicencias() throws Exception {
    }

    @Test
    public void testConsultas() throws Exception {
    }
}