/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;

/**
 *
 * @author JÑahui
 */
public class AltasYBajas {

    private EntityManager em;

    public AltasYBajas(EntityManager em) {
        this.em = em;
    }

    public void altaDePersona(Persona p, List<Vehiculo> listaV, List<LicenciaConductor> listaL) {
        if (em.find(Persona.class, p.getCi()) != null) {
            System.out.println("Ya existe una persona con dicha cedula: " + p.getCi());
        } else {
            for (Vehiculo vehiculo : listaV) {
                p.agregarVehiculo(vehiculo);
            }
            for (LicenciaConductor licenciaConductor : listaL) {
                p.agregarLicencia(licenciaConductor);
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

    public Persona acutualizarLicencias(Persona p) {
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