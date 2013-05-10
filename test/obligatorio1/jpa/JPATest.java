package obligatorio1.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    static ActualizadorBDYConsultasJPQL actualizador;
    static CreadorDeObjetos c;
    Date dNow = new Date();
    
    @BeforeClass
    public static void setUpClass() {
        actualizador = new ActualizadorBDYConsultasJPQL();
        c = new CreadorDeObjetos();
    }
    
    @AfterClass
    public static void tearDownClass() {
        if (actualizador.getEm() != null) {
            actualizador.getEm().close();
            actualizador.setEm(null);
        }
    }
    
    public void limpiarBD() {
        Query personas = actualizador.verificarConexion().createQuery("Select persona from Persona persona");
        List<Persona> idsPersona = personas.getResultList();
        for (Persona persona : idsPersona) {
            actualizador.bajaDePersona(persona.getCi());
        }
        Query vehiculos = actualizador.verificarConexion().createQuery("Select vehiculo from Vehiculo vehiculo");
        List<Vehiculo> idsVehiculo = vehiculos.getResultList();
        for (Vehiculo vehiculo : idsVehiculo) {
            actualizador.bajaDeVehiculo(vehiculo.getMatricula());
        }
        Query tipomoto = actualizador.verificarConexion().createQuery("Select tipomoto from TipoMoto tipomoto");
        List<TipoMoto> idsTipoMoto = tipomoto.getResultList();
        for (TipoMoto tipoMoto : idsTipoMoto) {
            actualizador.eliminarTipoMoto(tipoMoto.getId());
        }
        Query departamentosID = actualizador.verificarConexion().createQuery("Select departamento from Departamento departamento");
        List<Departamento> ids = departamentosID.getResultList();
        for (Departamento departamento : ids) {
            actualizador.eliminarDepartamento(departamento.getId());
        }
    }
    
    @Test
    public void testAgregarPersona() throws Exception {
        limpiarBD();
        cargarDepartamentos();
        Persona p = new Persona(45607876, "Jeasmine", "Bv Artigas");
        Departamento dep = actualizador.verificarConexion().find(Departamento.class, 6);
        LicenciaConductor li = new LicenciaConductor(23178758, "R", dNow, p, "Sin restriccion", dep);
        List<LicenciaConductor> licenciasDeConducir = new ArrayList<>();
        licenciasDeConducir.add(li);
        Auto v1 = new Auto(true, "jjj000", "1234567", "ABCD98765", "BMW", "2012", p);
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(v1);
        actualizador.altaDePersona(p, vehiculos, licenciasDeConducir);
        assertEquals(p, actualizador.verificarConexion().find(Persona.class, 45607876));
        assertEquals(p, actualizador.verificarConexion().find(Vehiculo.class, "jjj000").getDueño());
        assertEquals(p, actualizador.verificarConexion().find(LicenciaConductor.class, 23178758).getPropietario());
    }
    
    public List<Vehiculo> agregarVehiculos() {
        Persona p = new Persona(49757876, "Thomas", "Rivera");
        actualizador.altaDePersona(p, null, null);
        Vehiculo v2 = new Vehiculo("xxx000", "1234567", "ABCD98765", "MiniCooper", "2012", p);
        TipoMoto tipo = new TipoMoto(2, "De calle");
        c.crearTipoMoto(actualizador.verificarConexion(), tipo);
        Moto v4 = new Moto(tipo, "zzz000", "35347547", "GFJGFH45435", "Chevrolet", "2004", p);
        Camion v3 = new Camion(140, (short) 10, "lll000", "35457447", "JLMGFH45435", "Chevrolet", "2006", p);
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(v3);
        vehiculos.add(v2);
        vehiculos.add(v4);
        for (Vehiculo vehiculo : vehiculos) {
            actualizador.altaVehiculo(vehiculo);
        }
        return vehiculos;
    }
    
    @Test
    public void testBajaVehiculo() throws Exception {
        List<Vehiculo> vehiculos = agregarVehiculos();
        for (Vehiculo vehiculo : vehiculos) {
            assertEquals(vehiculo, actualizador.verificarConexion().find(Vehiculo.class, vehiculo.getMatricula()));
            actualizador.bajaDeVehiculo(vehiculo.getMatricula());
        }
        for (Vehiculo vehiculo : vehiculos) {
            assertEquals(null, actualizador.verificarConexion().find(Vehiculo.class, vehiculo.getMatricula()));
        }
    }
    
    private void cargarDepartamentos() {
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(1, "Artigas"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(2, "Salto"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(3, "Paysandú"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(4, "Rio Negro"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(5, "Colonia"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(6, "San José"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(7, "Montevideo"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(8, "Canelones"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(9, "Maldonado"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(10, "Rocha"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(11, "Lavalleja"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(12, "Treinta y Tres"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(13, "Florida"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(14, "Flores"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(15, "Durazno"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(16, "Cerro Largo"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(17, "Rivera"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(18, "Tacuarembó"));
        c.crearDepartamento(actualizador.verificarConexion(), new Departamento(19, "Soriano"));
    }
    
    @Test
    public void testActualizarLicencias() throws Exception {
        Persona p = new Persona(45203730, "Tomas", "Casita 1234");
        LicenciaConductor l1 = new LicenciaConductor(19283746, "A", dNow, p, "Sin restriccion", actualizador.verificarConexion().find(Departamento.class, 3));
        LicenciaConductor l2 = new LicenciaConductor( 96763746, "A", dNow, p, "Sin restriccion", actualizador.verificarConexion().find(Departamento.class, 4));
        LicenciaConductor l3 = new LicenciaConductor(13458536, "B", dNow, p, "Sin restriccion", actualizador.verificarConexion().find(Departamento.class, 6));
        List<LicenciaConductor> licencias = new ArrayList<>();
        licencias.add(l1);
        licencias.add(l2);
        licencias.add(l3);
        actualizador.altaDePersona(p, null, licencias);
        assertEquals(p, (actualizador.verificarConexion().find(LicenciaConductor.class, l1.getNumero())).getPropietario());
        assertEquals(p, (actualizador.verificarConexion().find(LicenciaConductor.class, l2.getNumero())).getPropietario());
        assertEquals(p, (actualizador.verificarConexion().find(LicenciaConductor.class, l3.getNumero())).getPropietario());
        
        Persona pActualizada = new Persona(45203730, null, null);
        LicenciaConductor l2modificada = new LicenciaConductor(actualizador.verificarConexion(), 96763746, "M", dNow, 45203730, "Tiene restriccion de velocidad", 4);
        LicenciaConductor l4nueva = new LicenciaConductor(actualizador.verificarConexion(), 52287450, "B", dNow, 45203730, "Sin restriccion", 14);
        List<LicenciaConductor> nuevasLicencias = new ArrayList<>();
        nuevasLicencias.add(l2modificada);
        nuevasLicencias.add(l4nueva);
        for (LicenciaConductor licenciaConductor : nuevasLicencias) {
            pActualizada.agregarLicencia(licenciaConductor);
        }
        System.out.println("Licencia antes de modificarse " + actualizador.verificarConexion().find(LicenciaConductor.class, l2.getNumero()));
        actualizador.actualizarLicencias(pActualizada);
        // No Contiene
        assertEquals(null, actualizador.verificarConexion().find(LicenciaConductor.class, 19283746));
        assertEquals(null, actualizador.verificarConexion().find(LicenciaConductor.class, 13458536));
        // Contiene
        assertEquals(l4nueva, actualizador.verificarConexion().find(LicenciaConductor.class, 52287450));
        assertEquals(l2modificada, actualizador.verificarConexion().find(LicenciaConductor.class, 96763746));
        System.out.println("Licencia antes de modificarse " + actualizador.verificarConexion().find(LicenciaConductor.class, l2.getNumero()));
    }
    
    @Test
    public void testConsultas() throws Exception {
    }
}