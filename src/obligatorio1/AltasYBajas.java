/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author JÑahui
 */
public class AltasYBajas {

    public void altaDePersona(Persona p, List<Vehiculo> listaV, List<LicenciaConductor> listaL, EntityManager em) {
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

    public void bajaDeVehiculo(Vehiculo v, EntityManager em) {
        try {
            Vehiculo vehiculo = em.find(v.getClass(), v.getMatricula());
            em.getTransaction().begin();
            em.remove(vehiculo);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acutualizarLicenciaVencimiento(LicenciaConductor l, Date vencimiento, EntityManager em) {
        try {
            LicenciaConductor licencia = em.find(LicenciaConductor.class, l.getNumero());
            if (licencia != null) {
                licencia.setVencimiento(vencimiento);
            }
        } catch (Exception e) {
        }
    }
}