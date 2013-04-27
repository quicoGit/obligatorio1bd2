/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.util.HashSet;
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
        EntityManager em = Persistence.createEntityManagerFactory("obligatorio1").createEntityManager();
//        ConsultasJPQL c = new ConsultasJPQL();
//        c.consultaLicPer(em);
        AltasYBajas prueba = new AltasYBajas(em);

        Set<LicenciaConductor> licencias = new HashSet<LicenciaConductor>();
        licencias.add(new LicenciaConductor(em, 23178938, "R", null, 45785403, null, 1));
        licencias.add(new LicenciaConductor(em, 23178939, "A", null, 45785403, null, 1));

        Persona persona = new Persona(45785403, null, null);
        persona.setLicenciasDeConducir(licencias);
        
        prueba.acutualizarLicencias(persona);

        em.close();
    }
}
