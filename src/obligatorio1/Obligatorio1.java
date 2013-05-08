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
        
        List<LicenciaConductor> licencias = new ArrayList<>();
        licencias.add(new LicenciaConductor(a.verificarConexion(), 23178938, "R", null, 45609876, null, 1));
        licencias.add(new LicenciaConductor(a.verificarConexion(), 23134739, "A", null, 45609876, null, 1));
        
        
        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(new Auto(a.verificarConexion(), true, "aaa000", "1234567", "ABCD98765", "BMW" , "2012", 45609876) );
        vehiculos.add(new Vehiculo(a.verificarConexion(), "bbb000", "65541723", "HSBC4321", "Bicy", "2010", 45609876));
        a.altaDePersona(p,null,licencias);

//        prueba.acutualizarLicencias(persona);

    }
}
