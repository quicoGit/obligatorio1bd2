package obligatorio1;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import obligatorio1.db4o.PersistenciaException;

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
                throw new PersistenciaException(e.getMessage(), e);
            }
        }
        throw new PersistenciaException("No se puede crear el vehiculo, ya existe uno bajo la matricula " + vec.getMatricula());
    }

    public void crearMoto(EntityManager em, Moto vec) {
        if (em.find(Vehiculo.class, vec.getMatricula()) == null) {
            if (em.find(TipoMoto.class, vec.getTipo().getId()) != null) {
                try {
                    em.getTransaction().begin();
                    em.persist(vec);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    throw new PersistenciaException(e.getMessage(), e);
                }
            } else {
                throw new PersistenciaException("No existe en la base de datos el tipoMoto " + vec.getTipo().getId() + " con el que se quiere asociar la moto " + vec.getMatricula());
            }
        } else {
            throw new PersistenciaException("No se puede crear la moto, ya existe una bajo la matricula " + vec.getMatricula());
        }
    }

    public void crearTipoMoto(EntityManager em, TipoMoto vec) {
        if (em.find(TipoMoto.class, vec.getId()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(vec);
                em.getTransaction().commit();
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            }

        } else {
            throw new PersistenciaException("No se puede crear el TipoMoto, ya existe una bajo el id " + vec.getId());
        }
    }

    public void crearCamion(EntityManager em, Camion vec) {
        if (em.find(Vehiculo.class, vec.getMatricula()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(vec);
                em.getTransaction().commit();
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            }
        }
        throw new PersistenciaException("No se puede crear el camion, ya existe uno bajo la matricula" + vec.getMatricula());
    }

    public void crearLicencia(EntityManager em, LicenciaConductor lic) {
        if (em.find(LicenciaConductor.class, lic.getNumero()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(lic);
                em.getTransaction().commit();
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            }
        }
        throw new PersistenciaException("No se puede crear la licencia, ya existe una bajo el numero " + lic.getNumero());

    }

    public void crearDepartamento(EntityManager em, Departamento d) {
        if (em.find(Departamento.class, d.getId()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(d);
                em.getTransaction().commit();
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            }
        } else {
            throw new PersistenciaException("No se pudo agregar ya existe un departamento con dicho id " + d.getId());

        }
    }
}
