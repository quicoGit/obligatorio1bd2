/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author JÑahui
 */
public class AltasYBajas {

    EntityManager em = Persistence.createEntityManagerFactory("obligatorio1").createEntityManager();

    public void altaDePersona(Persona p) {
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            System.out.println(em.find(Persona.class, p.getCi()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void bajaDeVehiculo(Vehiculo v) {
        try {
            Vehiculo vehiculo = em.find(v.getClass(), v.getMatricula());
            em.getTransaction().begin();
            em.remove(vehiculo);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}