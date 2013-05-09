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
 * @author Jeasty
 */
public class ActualizadorBDYConsultasJPQL {

    public ActualizadorBDYConsultasJPQL() {
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
            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
        }
        return getEm();
    }

    public boolean validarVehiculo(Vehiculo vehiculo, Persona p, EntityManager manager) {
        boolean resultado = false;
        if (vehiculo != null) {
            if (manager.find(Vehiculo.class, vehiculo.getMatricula()) != null) {
                resultado = true;
                System.out.println("No se puede agregar el vehiculo de matricula " + vehiculo.getMatricula() + " ya existe en la base de datos");
            }
            if (vehiculo.getDueño() != null) {
                if (vehiculo.getDueño().getCi() != p.getCi()) {
                    resultado = true;
                    System.out.println("El vehiculo que desea agregar esta asociada a otra persona de cedula " + p.getCi());
                }
            }
        }
        return resultado;
    }

    public boolean validarLicencia(LicenciaConductor licencia, Persona p, EntityManager manager) {
        boolean resultado = false;
        if (licencia != null) {
            if (manager.find(LicenciaConductor.class, licencia.getNumero()) != null) {
                resultado = true;
                System.out.println("No se puede agregar el vehiculo de matricula " + licencia.getNumero() + " ya existe en la base de datos");
            }
            if (licencia.getPropietario() != null) {
                if (licencia.getPropietario().getCi() != p.getCi()) {
                    resultado = true;
                    System.out.println("La licencia que desea agregar esta asociada a otra persona de cedula " + p.getCi());
                }
            }
            if (licencia.getDepartamento() == null) {
                resultado = true;
                System.out.println("No se pudo agregar la licencia de numero " + licencia.getNumero() + " no pertenece a un Departamento existente en la base de datos");
            }
        }
        return resultado;
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
                        manager.getTransaction().commit();
                        System.out.println("La eliminacion de la persona " + p.getCi() + " se realizo correctamente");
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
                System.out.println("Fallo la conexion");
            }
        } else {
            System.out.println("Se introdulo una persona nula, no se puedo ejecutar la peticion");
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
                        manager.getTransaction().commit();
                        System.out.println("La eliminacion de la licencia " + l.getNumero() + " se realizo correctamente");
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
                System.out.println("Fallo la conexion");
            }
        } else {
            System.out.println("Se introdulo una licencia nula, no se puedo ejecutar la peticion");
        }
    }

    public void altaDePersona(Persona p, List<Vehiculo> listaV, List<LicenciaConductor> listaL) {
        EntityManager manager = verificarConexion();
        if (p != null) {
            if (manager != null) {
                if (manager.find(Persona.class, p.getCi()) != null) {
                    System.out.println("No se pudo realizar el alta, ya existe una persona con dicha cedula: " + p.getCi());
                } else {
                    if (listaV != null) {
                        for (Vehiculo vehiculo : listaV) {
                            if (!validarVehiculo(vehiculo, p, manager)) {
                                if (vehiculo.getDueño() != null) {
                                    p.agregarVehiculo(vehiculo);
                                } else {
                                    vehiculo.setDueño(p);
                                    p.agregarVehiculo(vehiculo);
                                }
                            }
                        }
                    }
                    if (listaL != null) {
                        for (LicenciaConductor licenciaConductor : listaL) {
                            if (!validarLicencia(licenciaConductor, p, manager)) {
                                if (licenciaConductor.getPropietario() != null) {
                                    p.agregarLicencia(licenciaConductor);
                                } else {
                                    licenciaConductor.setPropietario(p);
                                    p.agregarLicencia(licenciaConductor);
                                }
                            }
                        }
                    }
                    try {
                        manager.getTransaction().begin();
                        manager.persist(p);
                        manager.getTransaction().commit();
                    } catch (Exception e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    } finally {
                        manager.close();
                        setEm(null);
                    }
                }
            } else {
                System.out.println("Fallo la conexion");
            }
        } else {
            System.out.println("Se introdulo una persona nula, no se puedo ejecutar la peticion");
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
                        manager.getTransaction().commit();
                        System.out.println("La eliminacion del vehiculo de matricula " + v.getMatricula() + " se realizo correctamente");
                    } catch (Exception e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    } finally {
                        manager.close();
                        setEm(null);
                    }
                } else {
                    System.out.println("El vehiculo de matricula " + v.getMatricula() + " no existe en la base de datos");
                }
            } else {
                System.out.println("Fallo la conexion");
            }
        } else {
            System.out.println("Se introdulo un vehiculo nulo, no se puedo ejecutar la peticion");
        }
    }

    public Persona acutualizarLicencias(Persona p) {
        EntityManager manager = verificarConexion();
        if (p != null) {
            if (manager != null) {
                if (manager.find(Persona.class, p.getCi()) != null) {
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
                    } finally {
                        manager.close();
                        setEm(null);
                    }
                } else {
                    System.out.println("No existe una persona en la base de datos con cedula " + p.getCi());
                }
            } else {
                System.out.println("Fallo la conexion");
            }
        } else {
            System.out.println("Se introdulo una persona nula, no se puedo ejecutar la peticion");
        }
        return null;
    }

    public void consultaPromedio() {
        EntityManager manager = verificarConexion();
        if (manager != null) {
            try {
                Query promedio = em.createQuery("Select count(vehiculo)/(Select count(persona)from Persona persona)from Vehiculo vehiculo");
                Long resPromedio = (Long) promedio.getSingleResult();
                if (resPromedio != null) {
                    System.out.println("Cantidad promedio de vehiculos por persona: " + resPromedio);
                }
                System.out.println("No hay resultado dado que no hay personas en la base de datos");
            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
            } finally {
                manager.close();
                setEm(null);
            }
        } else {
            System.out.println("Fallo la conexion");
        }
    }

    public void consultaLicPer() {
        EntityManager manager = verificarConexion();
        if (manager != null) {
            try {
                Query personas = em.createQuery("Select distinct(licencia.propietario)from LicenciaConductor licencia, LicenciaConductor licCon where "
                        + "licencia.departamento!=licCon.departamento and licencia.categoria=LicCon.categoria and licencia.propietario.ci=licCon.propietario.ci");
                List<Persona> listaPer = personas.getResultList();
                if (!listaPer.isEmpty()) {
                    for (Persona persona : listaPer) {
                        System.out.println("Personas que tienen más de una licencia del mismo tipo emitidas en distintos departamentos: " + persona);
                    }
                }
                System.out.println("No hay personas que tengan más de una licencia del mismo tipo emitidas en distintos departamentos");
            } catch (Exception e) {
            } finally {
                manager.close();
                setEm(null);
            }
        } else {
            System.out.println("Fallo la conexion");
        }
    }
}
