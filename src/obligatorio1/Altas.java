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
public class Altas {

    EntityManager em = Persistence.createEntityManagerFactory("obligatorio1").createEntityManager();

    public void altaDePersona(Persona p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }          
}
