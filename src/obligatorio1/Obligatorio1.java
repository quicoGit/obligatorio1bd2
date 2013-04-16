/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.util.List;
import java.util.Set;
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
        Persona per = em.find(Persona.class, 45603960);
        System.out.println(per);
        System.out.println(per.getLicenciasDeConducir());
        Vehiculo ve = em.find(Vehiculo.class, "aaa0000");
        System.out.println(ve);
        LicenciaConductor lic = em.find(LicenciaConductor.class, 27101054);        
//        Set<LicenciaConductor> licencia=per.getLicenciasDeConducir();
//        licencia.add(lic);
//        System.out.println(lic);
//        Departamento dep = em.find(Departamento.class, 1);
//        em.getTransaction().begin();
////////        em.persist(per);
////////        em.persist(new Persona(45603960, "Nahui", "8 de Octubre"));
//        em.persist(new TipoMoto(1, "Moto de Carrera"));
//        TipoMoto m = em.find(TipoMoto.class, 1);
//        lic.setPropietario(per);
//        System.out.println(m);
//        em.persist(new Moto(m, "bbb0000", "9876543", "123456789JFBMW", "BMW", "2009", per));
//////        em.persist(new Departamento(1, "Montevideo"));
////        em.persist(new LicenciaConductor(27101054, "A", null, null, dep));
//        em.getTransaction().commit();
        Query q1 = em.createQuery("Select licencia from LicenciaConductor licencia where licencia.departamento.nombre='Montevideo'");
//        Query q = em.createQuery("Select vehiculo from Vehiculo vehiculo where vehiculo.dueño.apellido='Nahui'");
//        q.setParameter("apellido_persona", "Nahui");
        List<LicenciaConductor> resultados = q1.getResultList();
        for (LicenciaConductor vehiculo : resultados) {

            System.out.println(vehiculo.getPropietario());
        }
        Query q = em.createQuery("Select persona from Persona persona where persona.ci = 45603960");
        List<Persona> resultados2 = q.getResultList();
        for (Persona p : resultados2) {
            for (Vehiculo vehiculo : p.getVehiculos()) {
                System.out.println(p.getCi());
                System.out.println(vehiculo);
            }
        }

//        Query q = em.createQuery("");
//        Query a = em.createQuery("delete * from Persona");
        em.close();
    }
}
