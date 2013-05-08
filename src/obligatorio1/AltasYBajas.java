/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author JÑahui
 */
public class AltasYBajas {

    public AltasYBajas() {
    }
    private EntityManager em = null;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EntityManager verificarConexion() {
        if (getEm() == null) {
            try {
                EntityManager manager = Persistence.createEntityManagerFactory("obligatorio1").createEntityManager();
                setEm(manager);
                return manager;
            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
        }
        return getEm();

    }

    public void bajaDePersona(Persona p) {
        EntityManager manager = verificarConexion();
        if (p != null) {
            if (manager != null) {
                if (manager.find(Persona.class, p.getCi()) != null) {
                    try {
                        Persona mergedMember = manager.merge(manager.find(Persona.class, p.getCi()));
                        manager.getTransaction().begin();
                        manager.remove(mergedMember);
                        System.out.println("La eliminacion de la persona " + p.getCi() + " se realizo correctamente");
                        manager.getTransaction().commit();
                    } catch (Exception e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    } finally {
                        manager.close();
                        setEm(null);
                    }
                } else {
                    System.out.println("La persona ha elminar no se encuentra en la base de datos");
                }
            } else {
                System.out.println("No se pudo realizar la operacion, la conexion falló");
            }
        }
    }

    public void bajaDeLicencia(LicenciaConductor l) {
        EntityManager manager = verificarConexion();
        if (l != null) {
            if (manager != null) {
                if (manager.find(LicenciaConductor.class, l.getNumero()) != null) {
                    try {
                        LicenciaConductor mergedMember = manager.merge(manager.find(LicenciaConductor.class, l.getNumero()));
                        manager.getTransaction().begin();
                        manager.remove(mergedMember);
                        System.out.println("La eliminacion de la licencia " + l.getNumero() + " se realizo correctamente");
                        manager.getTransaction().commit();
                    } catch (Exception e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    } finally {
                        manager.close();
                        setEm(null);
                    }
                } else {
                    System.out.println("La licencia ha elminar no se encuentra en la base de datos");
                }
            } else {
                System.out.println("No se pudo realizar la operacion, la conexion falló");
            }
        }
    }

    public void altaDePersona(Persona p, List<Vehiculo> listaV, List<LicenciaConductor> listaL) {
        EntityManager manager = verificarConexion();
        if (manager != null) {
            if (manager.find(Persona.class, p.getCi()) != null) {
                System.out.println("No se pudo realizar el alta, ya existe una persona con dicha cedula: " + p.getCi());
            } else {
                if (listaV != null) {
                    for (Vehiculo vehiculo : listaV) {
                        if (vehiculo.getDueño() != null) {
                            p.agregarVehiculo(vehiculo);
                        } else {
                            vehiculo.setDueño(p);
                            p.agregarVehiculo(vehiculo);
                        }
                    }
                }
                if (listaL != null) {
                    for (LicenciaConductor licenciaConductor : listaL) {
                        if (licenciaConductor.getDepartamento() != null) {
                            if (licenciaConductor.getPropietario() != null) {
                                p.agregarLicencia(licenciaConductor);
                            } else {
                                licenciaConductor.setPropietario(p);
                                p.agregarLicencia(licenciaConductor);
                            }
                        } else {
                            System.out.println("La licencia " + licenciaConductor.getNumero() + " no se pudo agregar dado que no es de ningun departamento conocido");
                        }
                    }
                }
                try {
                    manager.getTransaction().begin();
                    manager.persist(p);
                    manager.getTransaction().commit();
                } catch (Exception e) {
                    System.out.println("Exception caught: " + e.getMessage());
                }
            }
        }
    }

    public void bajaDeVehiculo(Vehiculo v) {
        EntityManager manager = verificarConexion();
        if (v != null) {
            if (manager != null) {
                if (manager.find(v.getClass(), v.getMatricula()) != null) {
                    try {
                        Vehiculo mergedMember = manager.merge(manager.find(Vehiculo.class, v.getMatricula()));
                        manager.getTransaction().begin();
                        manager.remove(mergedMember);
                        System.out.println("La eliminacion del vehiculo de matricula " + v.getMatricula() + " se realizo correctamente");
                        manager.getTransaction().commit();
                    } catch (Exception e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                } else {
                    System.out.println("El vehiculo de matricula " + v.getMatricula() + " no existe en la base de datos");
                }
            }
        }

    }

    public Persona acutualizarLicencias(Persona p) {
        EntityManager manager = verificarConexion();
        try {
            Persona persona = manager.find(Persona.class, p.getCi());

            manager.getTransaction().begin();
            List<LicenciaConductor> eliminar = new ArrayList<>();
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
                manager.remove(licenciaConductor);
            }

            for (LicenciaConductor nueva : p.getLicenciasDeConducir()) {
                if (!persona.getLicenciasDeConducir().contains(nueva)) {
                    persona.agregarLicencia(nueva);
                }
            }

            manager.persist(persona);
            manager.getTransaction().commit();
            return persona;
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            return null;
        }
    }
}