/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author JÑahui
 */
public class AltasYBajas {

    public AltasYBajas() {
    }

    public EntityManager verificarConexion() {
        try {
            EntityManager em = Persistence.createEntityManagerFactory("obligatorio1").createEntityManager();
            return em;
        } catch (Exception e) {
            return null;
        }
    }

    public void bajaDePersona(Persona p) {
        EntityManager em = verificarConexion();
        if (em != null && p != null) {
            if (em.find(Persona.class, p.getCi()) != null) {
                try {
                    Persona mergedMember = em.merge(em.find(Persona.class, p.getCi()));
                    em.getTransaction().begin();
                    em.remove(mergedMember);
                    System.out.println("La eliminacion de la persona " + p.getCi() + " se realizo correctamente");
                    em.getTransaction().commit();
                } catch (Exception e) {
                    System.out.println("Exception caught: " + e.getMessage());
                }
            } else {
                System.out.println("La persona ha elminar no se encuentra en la base de datos");
            }
        } else {
            System.out.println("No se pudo realizar la operacion, la conexion falló");
        }
        em.close();
    }

    public void altaDePersona(Persona p, List<Vehiculo> listaV, List<LicenciaConductor> listaL) {
        EntityManager em = verificarConexion();
        if (em != null) {
            if (em.find(Persona.class, p.getCi()) != null) {
                System.out.println("No se pudo realizar el alta, ya existe una persona con dicha cedula: " + p.getCi());
            } else {
                if (listaV != null) {
                    for (Vehiculo vehiculo : listaV) {
                        p.agregarVehiculo(vehiculo);
                    }
                }
                if (listaL != null) {
                    for (LicenciaConductor licenciaConductor : listaL) {
                        if (em.find(LicenciaConductor.class, licenciaConductor.getDepartamento().getId()) != null) {
                            p.agregarLicencia(licenciaConductor);
                        } else {
                            System.out.println("La licencia "+licenciaConductor.getNumero()+"no se pudo agregar dado que no es de ningun departamento conocido");
                        }
                    }
                }

                try {
                    em.getTransaction().begin();
                    em.persist(p);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void bajaDeVehiculo(Vehiculo v) {
        EntityManager em = verificarConexion();
        try {
            Vehiculo vehiculo = em.find(v.getClass(), v.getMatricula());
            em.getTransaction().begin();
            em.remove(vehiculo);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Persona acutualizarLicencias(Persona p) {
        EntityManager em = verificarConexion();
        try {
            Persona persona = em.find(Persona.class, p.getCi());

            em.getTransaction().begin();
            List<LicenciaConductor> eliminar = new ArrayList<LicenciaConductor>();
            for (LicenciaConductor vieja : persona.getLicenciasDeConducir()) {
                boolean remover = true;
                for (LicenciaConductor nueva : p.getLicenciasDeConducir()) {
                    if (nueva.getNumero() == vieja.getNumero()) {
                        vieja.copy(nueva);
                        remover = false;
                        break;
                    }
                }
                if (remover) {
                    eliminar.add(vieja);
                }
            }

            for (LicenciaConductor licenciaConductor : eliminar) {
                persona.removerLicencia(licenciaConductor);
                em.remove(licenciaConductor);
            }

            for (LicenciaConductor nueva : p.getLicenciasDeConducir()) {
                if (!persona.getLicenciasDeConducir().contains(nueva)) {
                    persona.agregarLicencia(nueva);
                }
            }

            em.persist(persona);
            em.getTransaction().commit();
            return persona;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}