/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

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
        ConsultasJPQL c = new ConsultasJPQL();
        c.consultaLicPer(em);
        em.close();
    }
}
