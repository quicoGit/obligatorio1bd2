package obligatorio1.db4o;

import java.io.File;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import obligatorio1.db4o.modelo.Auto;
import obligatorio1.db4o.modelo.Departamento;
import obligatorio1.db4o.modelo.LicenciaConductor;
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

    @Rule
    public ExpectedException exception = ExpectedException.none();
    static AltasYBajas altasybajas;

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

        assertEquals(altasybajas.getPersonas().find(45203730), p);
        assertEquals(altasybajas.getPersonas().find(45203730).getVehiculos().toArray(new Vehiculo[]{})[0], fiatUno);
        assertEquals(altasybajas.getPersonas().find(45203730).getVehiculos().toArray(new Vehiculo[]{})[0].getDueño(), p);
    }

    @Test
    public void testAgregarPersonaDuplicada() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = Arrays.asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);
        exception.expect(PersistenciaException.class);
        altasybajas.altaDePersona(p, null, null);

        assertEquals(altasybajas.getPersonas().find(45203730), p);
    }

    @Test
    public void testAgregarPersonaMismaCedula() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);

        exception.expect(PersistenciaException.class);
        altasybajas.altaDePersona(new Persona(45203730, "John", "House"), null, null);

        assertEquals(altasybajas.getPersonas().find(45203730), p);
    }

    @Test
    public void testBorrarPersona() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);
        assertEquals(altasybajas.getPersonas().find(45203730), p);

        altasybajas.bajaDePersona(p);
        assertEquals(altasybajas.getPersonas().find(45203730), null);

        altasybajas.altaDePersona(p, null, null);
        assertEquals(altasybajas.getPersonas().find(45203730), p);

        altasybajas.bajaDePersona(p);
        assertEquals(altasybajas.getPersonas().find(45203730), null);
    }

    @Test
    public void testAgregarVehiculoDiferenteDueño() throws Exception {
        Persona tomas = new Persona(45203730, "Tomas", "Casita 1234");
        Persona jeasmine = new Persona(1234567, "Jeasmine", "Apto 4321");

        Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", tomas);
        Auto fiatDos = new Auto(true, "AABBCC", "5", "4", "Fiat", "Uno", jeasmine);

        List<Vehiculo> vehiculos = asList((Vehiculo) fiatUno, (Vehiculo) fiatDos);

        exception.expect(PersistenciaException.class);
        altasybajas.altaDePersona(tomas, vehiculos, null);
        altasybajas.altaDePersona(jeasmine, vehiculos, null);
    }

    @Test
    public void testAgregarVehiculoMatriculaDuplicada() throws Exception {
        Persona tomas = new Persona(45203730, "Tomas", "Casita 1234");
        Persona jeasmine = new Persona(1234567, "Jeasmine", "Apto 4321");

        Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", tomas);
        Auto fiatDos = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", jeasmine);

        List<Vehiculo> vehiculosTomas = asList((Vehiculo) fiatUno);
        List<Vehiculo> vehiculosJeasmine = asList((Vehiculo) fiatDos);

        altasybajas.altaDePersona(tomas, vehiculosTomas, null);
        exception.expect(PersistenciaException.class);
        altasybajas.altaDePersona(jeasmine, vehiculosJeasmine, null);
    }

    @Test
    public void testBorrarPersonaDejaVehiculo() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);
        assertEquals(fiatUno, altasybajas.getVehiculos().find("AABB"));

        altasybajas.bajaDePersona(p);
        assertEquals(altasybajas.getPersonas().find(45203730), null);

        assertEquals(fiatUno, altasybajas.getVehiculos().find("AABB"));
        assertEquals(null, altasybajas.getVehiculos().find("AABB").getDueño());
    }

    @Test
    public void testBorrarPersonaInexistente() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        exception.expect(PersistenciaException.class);
        altasybajas.bajaDePersona(p);
        altasybajas.bajaDePersona(p);
    }

    @Test
    public void testBorrarVehiculo() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);

        altasybajas.bajaDeVehiculo(fiatUno);

        assertEquals(null, altasybajas.getVehiculos().find("AABB"));
        assertEquals(true, altasybajas.getPersonas().find(45203730).getVehiculos().isEmpty());
    }

    @Test
    public void testConsultas() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = asList((Vehiculo) fiatUno);

        altasybajas.altaDePersona(p, vehiculos, null);

        assertEquals(Consultas.consultaPromedio(altasybajas.getDb()), 1, 0.01);
    }

    private void cargarDepartamentos() {
        altasybajas.getDb().store(new Departamento(1, "Artigas"));
        altasybajas.getDb().store(new Departamento(2, "Salto"));
        altasybajas.getDb().store(new Departamento(3, "Paysandú"));
        altasybajas.getDb().store(new Departamento(4, "Rio Negro"));
        altasybajas.getDb().store(new Departamento(5, "Colonia"));
        altasybajas.getDb().store(new Departamento(6, "San José"));
        altasybajas.getDb().store(new Departamento(7, "Montevideo"));
        altasybajas.getDb().store(new Departamento(8, "Canelones"));
        altasybajas.getDb().store(new Departamento(9, "Maldonado"));
        altasybajas.getDb().store(new Departamento(10, "Rocha"));
        altasybajas.getDb().store(new Departamento(11, "Lavalleja"));
        altasybajas.getDb().store(new Departamento(12, "Treinta y Tres"));
        altasybajas.getDb().store(new Departamento(13, "Florida"));
        altasybajas.getDb().store(new Departamento(14, "Flores"));
        altasybajas.getDb().store(new Departamento(15, "Durazno"));
        altasybajas.getDb().store(new Departamento(16, "Cerro Largo"));
        altasybajas.getDb().store(new Departamento(17, "Rivera"));
        altasybajas.getDb().store(new Departamento(18, "Tacuarembó"));
        altasybajas.getDb().store(new Departamento(19, "Soriano"));
    }

    private Departamento departamentoById(int id) {
        return altasybajas.getDepartamentos().get(id);
    }

    @Test
    public void testActualizarLicencias() throws Exception {
        cargarDepartamentos();

        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = asList((Vehiculo) fiatUno);

        LicenciaConductor l1 = new LicenciaConductor(10, "A", new Date(2020, 5, 10), p, "as", departamentoById(7));
        LicenciaConductor l2 = new LicenciaConductor(11, "B", new Date(2020, 5, 11), p, "as2", departamentoById(7));
        LicenciaConductor l3 = new LicenciaConductor(12, "C", new Date(2020, 5, 12), p, "1234", departamentoById(6));
        List<LicenciaConductor> licencias = asList(l1, l2, l3);

        altasybajas.altaDePersona(p, vehiculos, licencias);

        Persona jeasmine = new Persona(1234567, "Jeasmine", "Apto 4321");
        Auto fiatDos = new Auto(true, "AABBCC", "5", "4", "Fiat", "Uno", jeasmine);

        altasybajas.altaDePersona(jeasmine, asList((Vehiculo) fiatDos), asList(new LicenciaConductor(20, "A", new Date(2020, 5, 10), jeasmine, "123", departamentoById(7))));

        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l1));
        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l2));
        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l3));

        Persona pActualizada = new Persona(45203730, null, null);
        LicenciaConductor l2modificada = new LicenciaConductor(11, "H", new Date(2030, 5, 11), pActualizada, "ss", departamentoById(1));
        LicenciaConductor l4nueva = new LicenciaConductor(15, "C", new Date(2020, 1, 12), pActualizada, "aa", departamentoById(17));
        List<LicenciaConductor> nuevasLicencias = asList(l2modificada, l3, l4nueva);
        Set<LicenciaConductor> set = new HashSet<>();
        set.addAll(nuevasLicencias);
        pActualizada.setLicenciasDeConducir(set);

        altasybajas.actualizarLicencias(pActualizada);

        // Licencias
        System.out.println("Licencias Actualizadas");
        for (LicenciaConductor licenciaConductor : altasybajas.getPersonas().get(45203730).getLicenciasDeConducir()) {
            System.out.println(licenciaConductor);
            if (licenciaConductor.equals(l2modificada)) {
                System.out.println("L2 mod!!");
                System.out.println(altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l2modificada));
            }
        }

        System.out.println("L2: " + l2modificada);

        // Contiene
        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l4nueva));
        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l3));
        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l2modificada));

        // No contiene
        assertEquals(false, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l1));
        assertEquals(false, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l2));
    }

    @Test
    public void testConsultaLicPer() throws Exception {
        cargarDepartamentos();

        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        final Auto fiatUno = new Auto(false, "AABB", "5", "4", "Fiat", "Uno", p);
        List<Vehiculo> vehiculos = asList((Vehiculo) fiatUno);

        LicenciaConductor l1 = new LicenciaConductor(10, "A", new Date(2020, 5, 10), p, null, departamentoById(7));
        LicenciaConductor l2 = new LicenciaConductor(11, "B", new Date(2020, 5, 11), p, null, departamentoById(7));
        LicenciaConductor l3 = new LicenciaConductor(12, "C", new Date(2020, 5, 12), p, null, departamentoById(6));
        LicenciaConductor l4 = new LicenciaConductor(13, "C", new Date(2020, 5, 12), p, null, departamentoById(17));
        List<LicenciaConductor> licencias = asList(l1, l2, l3, l4);

        altasybajas.altaDePersona(p, vehiculos, licencias);

        Persona jeasmine = new Persona(1234567, "Jeasmine", "Apto 4321");
        Auto fiatDos = new Auto(true, "AABBCC", "5", "4", "Fiat", "Uno", jeasmine);

        altasybajas.altaDePersona(jeasmine, asList((Vehiculo) fiatDos), asList(new LicenciaConductor(20, "A", new Date(2020, 5, 10), jeasmine, null, departamentoById(7))));

        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l1));
        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l2));
        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l3));
        assertEquals(true, altasybajas.getPersonas().get(45203730).getLicenciasDeConducir().contains(l4));

        List<Persona> multiplesLicencias = Consultas.consultaLicPer(altasybajas.getDb());

        assertEquals(1, multiplesLicencias.size());
        assertEquals(true, multiplesLicencias.contains(p));
        assertEquals(false, multiplesLicencias.contains(jeasmine));
    }
}