/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import javax.persistence.EntityManager;

/**
 *
 * @author JÑahui
 */
public class CreadorDeObjetos {

    
    public void crearAuto(EntityManager em, Auto vec) {
        try {
            em.getTransaction().begin();
            em.persist(vec);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Ese auto ya existe");
        }

    }

    public void crearMoto(EntityManager em, Moto vec) {
        try {
            em.getTransaction().begin();
            em.persist(vec);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Esa moto ya existe");
        }

    }

    public void crearCamion(EntityManager em, Camion vec) {
        try {
            em.getTransaction().begin();
            em.persist(vec);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Ese auto ya existe");
        }

    }

    public void crearLicencia(EntityManager em, LicenciaConductor lic) {
        try {
            em.getTransaction().begin();
            em.persist(lic);
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Esa licencia ya existe");
        }

    }

    public void crearDepartamento(EntityManager em, Departamento d) {
        try {
            em.getTransaction().begin();
            em.persist(d);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Exception caught: " +  e. getMessage());   
        }

    }
}
