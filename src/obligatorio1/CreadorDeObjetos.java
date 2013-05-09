package obligatorio1;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author JÑahui
 */
public class CreadorDeObjetos {

    public void crearAuto(EntityManager em, Auto vec) {
        if (em.find(Vehiculo.class, vec.getMatricula()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(vec);
                em.getTransaction().commit();

            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
        }
        System.out.println("Ya existe un Vehiculo con el numero de matricula " + vec.getMatricula());
    }

    public void crearMoto(EntityManager em, Moto vec) {
        if (em.find(Vehiculo.class, vec.getMatricula()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(vec);
                em.getTransaction().commit();

            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
        }
        System.out.println("Ya existe un Vehiculo con el numero de matricula " + vec.getMatricula());
    }

    public void crearCamion(EntityManager em, Camion vec) {
        if (em.find(Vehiculo.class, vec.getMatricula()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(vec);
                em.getTransaction().commit();

            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
        }
        System.out.println("Ya existe un Vehiculo con el numero de matricula " + vec.getMatricula());
    }

    public void crearLicencia(EntityManager em, LicenciaConductor lic) {
        if (em.find(LicenciaConductor.class, lic.getNumero()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(lic);
                em.getTransaction().commit();

            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
        }
        System.out.println("Ya existe una licencia con el numero " + lic.getNumero());
    }

    public void crearDepartamento(EntityManager em, Departamento d) {
        if (em.find(Departamento.class, d.getId()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(d);
                em.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
        }
        System.out.println("Ya existe un departamento con dicho id " + d.getId());
        Query departamentosID = em.createQuery("Select departamento.id from Departamento departamento");
        List ids = departamentosID.getResultList();
        System.out.println("Los IDs que no se pueden utilizar son " + ids.toString());

    }
}
