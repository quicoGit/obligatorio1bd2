/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import obligatorio1.db4o.PersistenciaException;

/**
 *
 * @author JÑahui
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
                throw new PersistenciaException("Fallo la conexion", e);
            }
        }
        return getEm();
    }

    public boolean validarVehiculo(Vehiculo vehiculo, Persona p, EntityManager manager) {
        boolean resultado = false;
        if (vehiculo != null) {
            if (manager.find(Vehiculo.class, vehiculo.getMatricula()) != null) {
                resultado = true;
                System.out.println("No se pudo agregar el vehiculo de matricula " + vehiculo.getMatricula() + " ya existe en la base de datos");
            }
            if (vehiculo.getDueño() != null) {
                if (vehiculo.getDueño().getCi() != p.getCi()) {
                    resultado = true;
                    System.out.println("No se pudo agregar el vehiculo de matricula " + vehiculo.getMatricula() + " esta asociado a otra persona de cedula " + vehiculo.getDueño().getCi());
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
                System.out.println("No se pudo agregar la licencia de numero " + licencia.getNumero() + " ya existe en la base de datos");
            }
            if (licencia.getPropietario() != null) {
                if (licencia.getPropietario().getCi() != p.getCi()) {
                    resultado = true;
                    System.out.println("La licencia de numero " + licencia.getNumero() + " no se pudo agregar, ya esta asociada a otra persona de cedula " + p.getCi());
                }
            }
            if (manager.find(Departamento.class, licencia.getDepartamento().getId()) == null) {
                resultado = true;
                System.out.println("No se pudo agregar la licencia de numero " + licencia.getNumero() + " no pertenece a un Departamento existente en la base de datos");
            }
        }
        return resultado;
    }

    public void altaVehiculo(Vehiculo v) {
        EntityManager manager = verificarConexion();
        if (manager.find(Vehiculo.class, v.getMatricula()) == null) {
            try {
                manager.getTransaction().begin();
                manager.persist(v);
                manager.getTransaction().commit();
                System.out.println("El alta del vehiculo de matricula " + v.getMatricula() + " se realizo correctamente");
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            }
        } else {
            throw new PersistenciaException("No se pudo agregar a la base de datos el vehiculo de matricula " + v.getMatricula() + " dado que ya existe uno con la misma matricula");
        }
    }

    public void eliminarDepartamento(int id) {
        EntityManager manager = verificarConexion();
        if (manager.find(Departamento.class, id) != null) {
            try {
                Departamento mergedMember = manager.merge(manager.find(Departamento.class, id));
                manager.getTransaction().begin();
                manager.remove(mergedMember);
                manager.getTransaction().commit();
                System.out.println("La eliminacion del Departamento de id " + id + " se realizo correctamente");
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            }
        } else {
            throw new PersistenciaException("No existe en la base de datos el Departamento a eliminar");
        }
    }

    public void eliminarTipoMoto(int id) {
        EntityManager manager = verificarConexion();
        if (manager.find(TipoMoto.class, id) != null) {
            try {
                TipoMoto mergedMember = manager.merge(manager.find(TipoMoto.class, id));
                for (Moto vehiculo : mergedMember.getMotos()) {
                    vehiculo.setTipo(null);
                    manager.getTransaction().begin();
                    manager.persist(vehiculo);
                    manager.getTransaction().commit();
                }
                manager.getTransaction().begin();
                manager.remove(mergedMember);
                manager.getTransaction().commit();
                System.out.println("La eliminacion del TipoMoto de id " + id + " se realizo correctamente");
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            } finally {
                manager.close();
                setEm(null);
            }
        } else {
            throw new PersistenciaException("No existe en la base de datos el TipoMoto a eliminar");
        }
    }

    public void bajaDePersona(int ci) {
        EntityManager manager = verificarConexion();
        if (ci <= 0) {
            System.out.println("Se introdujo una cedula invalida, no se puedo ejecutar la peticion");
            return;
        }
        if (manager.find(Persona.class, ci) != null) {
            try {
                Persona mergedMember = manager.merge(manager.find(Persona.class, ci));
                for (Vehiculo vehiculo : mergedMember.getVehiculos()) {
                    vehiculo.setDueño(null);
                    manager.getTransaction().begin();
                    manager.persist(vehiculo);
                    manager.getTransaction().commit();
                }
                manager.getTransaction().begin();
                manager.remove(mergedMember);
                manager.getTransaction().commit();
                System.out.println("La eliminacion de la persona " + ci + " se realizo correctamente");
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            } finally {
                manager.close();
                setEm(null);
            }
        } else {
            throw new PersistenciaException("No existe en la base de datos la persona a eliminar");
        }
    }

    public void bajaDeLicencia(int numero) {
        EntityManager manager = verificarConexion();
        if (numero <= 0) {
            System.out.println("Se introdulo una licencia nula, no se puedo ejecutar la peticion");
            return;
        }
        if (manager.find(LicenciaConductor.class, numero) != null) {
            try {
                LicenciaConductor mergedMember = manager.merge(manager.find(LicenciaConductor.class, numero));
                if (manager.find(LicenciaConductor.class, numero).getDepartamento() != null) {
                    if (manager.find(Departamento.class, mergedMember.getDepartamento().getId()) == null) {
                        mergedMember.setDepartamento(null);
                        manager.getTransaction().begin();
                        manager.persist(mergedMember);
                        manager.getTransaction().commit();
                    }
                }
                manager.getTransaction().begin();
                manager.remove(mergedMember);
                manager.getTransaction().commit();
                System.out.println("La eliminacion de la licencia " + numero + " se realizo correctamente");
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            } finally {
                manager.close();
                setEm(null);
            }
        } else {
            throw new PersistenciaException("No existe en la base de datos la licencia a eliminar");
        }
    }

//METODOS OBLIGATORIOS
    public void altaDePersona(Persona p, List<Vehiculo> listaV, List<LicenciaConductor> listaL) {
        EntityManager manager = verificarConexion();
        if (manager.find(Persona.class, p.getCi()) != null) {
            throw new PersistenciaException("No se pudo realizar el alta, ya existe una persona con cedula: " + p.getCi());
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
                throw new PersistenciaException(e.getMessage(), e);
            } finally {
                manager.close();
                setEm(null);
            }
        }
    }

    public void bajaDeVehiculo(String matricula) {
        EntityManager manager = verificarConexion();
        if (manager.find(Vehiculo.class, matricula) != null) {
            try {
                Vehiculo mergedMember = manager.merge(manager.find(Vehiculo.class, matricula));
                manager.getTransaction().begin();
                manager.remove(mergedMember);
                manager.getTransaction().commit();
                System.out.println("La eliminacion del vehiculo de matricula " + matricula + " se realizo correctamente");
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            } finally {
                manager.close();
                setEm(null);
            }
        } else {
            throw new PersistenciaException("No se pudo eliminar el vehiculo de matricula " + matricula + " dado que no existe en la base de datos");
        }
    }

    public Persona actualizarLicencias(Persona p) {
        EntityManager manager = verificarConexion();
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
                    if (!contieneLicenciaPorCategoria(persona.getLicenciasDeConducir(), nueva)) {
                        if (manager.find(LicenciaConductor.class, nueva.getNumero()) == null) {
                            if (nueva.getPropietario().getCi() == persona.getCi()) {
                                persona.agregarLicencia(nueva);
                            } else {
                                System.out.println("La licencia de numero " + nueva.getNumero() + "no se pudo actualizar, dado que esta asociada a otra persona");
                            }
                        }
                    }
                }
                manager.persist(persona);
                manager.getTransaction().commit();
                return persona;
            } catch (Exception e) {
                throw new PersistenciaException(e.getMessage(), e);
            } finally {
                manager.close();
                setEm(null);
            }
        } else {
            throw new PersistenciaException("No se pudo actualiza la persona de cedula " + p.getCi() + " dado que no existe en la base de datos");
        }
    }

    private boolean contieneLicenciaPorCategoria(Set<LicenciaConductor> licencias, LicenciaConductor licencia) {
        for (LicenciaConductor l : licencias) {
            if (l.getCategoria().equals(licencia.getCategoria())) {
                return true;
            }
        }
        return false;
    }

    //CONSULTAS JPQL
    public void consultaPromedio() {
        EntityManager manager = verificarConexion();
        try {
            Query promedio = em.createQuery("Select count(vehiculo)/(Select count(persona)from Persona persona)from Vehiculo vehiculo");
            Long resPromedio = (Long) promedio.getSingleResult();
            if (resPromedio != null) {
                System.out.println("Cantidad promedio de vehiculos por persona: " + resPromedio);
            } else {
                System.out.println("No hay resultado dado que no hay personas en la base de datos");
            }
        } catch (Exception e) {
            throw new PersistenciaException(e.getMessage(), e);
        } finally {
            manager.close();
            setEm(null);
        }
    }

    public void consultaLicPer() {
        EntityManager manager = verificarConexion();
        try {
            Query personas = em.createQuery("Select distinct(licencia.propietario)from LicenciaConductor licencia, LicenciaConductor licCon where "
                    + "licencia.departamento!=licCon.departamento and licencia.categoria=LicCon.categoria and licencia.propietario.ci=licCon.propietario.ci");
            List<Persona> listaPer = personas.getResultList();
            if (!listaPer.isEmpty()) {
                for (Persona persona : listaPer) {
                    System.out.println("Personas que tienen más de una licencia del mismo tipo emitidas en distintos departamentos: " + persona);
                }
            } else {
                System.out.println("No hay personas que tengan más de una licencia del mismo tipo emitidas en distintos departamentos");
            }
        } catch (Exception e) {
            throw new PersistenciaException(e.getMessage(), e);
        } finally {
            manager.close();
            setEm(null);
        }
    }
}
