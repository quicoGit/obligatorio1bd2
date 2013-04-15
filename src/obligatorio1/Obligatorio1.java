/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.util.List;
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
        Persona per = em.find(Persona.class, 45603960);
        System.out.println(per);
        Vehiculo ve = em.find(Vehiculo.class, "aaa0000");
        System.out.println(ve);
//        em.getTransaction().begin();
////        em.persist(per);
////        em.persist(new Persona(45603960, "Nahui", "8 de Octubre"));
//        em.persist(new Vehiculo("aaa0000", "123456789", "123456789LMHSBC", "MiniCooper", "2012", per));
//        em.getTransaction().commit();
        Query q1 = em.createQuery("Select persona from Persona persona where persona.domicilio='BrEspaña'");
        Query q = em.createQuery("Select vehiculo from Vehiculo vehiculo where vehiculo.dueño.apellido='Nahui'");
//        q.setParameter("apellido_persona", "Nahui");
        List<Vehiculo> resultados = q.getResultList();
        for (Vehiculo vehiculo : resultados) {
            System.out.println(vehiculo.getMarca());
            System.out.println(vehiculo);
        }
//        Query q = em.createQuery("Select persona from Persona persona where persona.ci = 45603960");
        List<Persona> resultados2 = q1.getResultList();
        for (Persona p : resultados2) {

            System.out.println(p.getCi());
            System.out.println(p);
        }

//        Query q = em.createQuery("");
//        Query a = em.createQuery("delete * from Persona");
        em.close();
    }
}
