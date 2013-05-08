/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author JÑahui
 */
public class Obligatorio1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
//        ConsultasJPQL c = new ConsultasJPQL();
//        c.consultaLicPer(em);
        AltasYBajas a = new AltasYBajas();
        Persona p = new Persona(45609876, "Jeasmine", "Bv Artigas");
        LicenciaConductor li = new LicenciaConductor(a.verificarConexion(), 23178938, "R", null, 45609876, null, 1);
        LicenciaConductor lo = new LicenciaConductor(a.verificarConexion(), 23134739, "A", null, 45609876, null, 1);
        List<LicenciaConductor> licencias = new ArrayList<>();
        licencias.add(li);
        licencias.add(lo);        
        List<Vehiculo> vehiculos = new ArrayList<>();
        Auto v1 = new Auto(a.verificarConexion(), true, "aaa000", "1234567", "ABCD98765", "BMW" , "2012", 45609876);
        vehiculos.add(v1 );
        Vehiculo v2 = new Vehiculo(a.verificarConexion(), "bbb000", "65541723", "HSBC4321", "Bicy", "2010", 45609876);
        vehiculos.add(v2);
//        for (Vehiculo vehiculo : vehiculos) {
//            a.bajaDeVehiculo(vehiculo);            
//        }
//        a.bajaDePersona(p);
//        prueba.acutualizarLicencias(persona);
        a.altaDePersona(p, vehiculos, licencias);   
    }
}
