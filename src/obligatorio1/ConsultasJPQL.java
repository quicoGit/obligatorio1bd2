package obligatorio1;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author JÑahui
 */
public class ConsultasJPQL {

    public void consultaPromedio(EntityManager em) {
        Query promedio = em.createQuery("Select count(vehiculo)/(Select count(persona)from Persona persona)from Vehiculo vehiculo");
        Long resPromedio = (Long) promedio.getSingleResult();
        if (resPromedio != null) {
            System.out.println("Cantidad promedio de vehiculos por persona: " + resPromedio);
        }
        System.out.println("Cantidad promedio de vehiculos por persona: 0");
    }

    public void consultaLicPer(EntityManager em) {
        Query personas = em.createQuery("Select distinct(licencia.propietario)from LicenciaConductor licencia, LicenciaConductor licCon where "
                + "licencia.departamento!=licCon.departamento and licencia.categoria=LicCon.categoria and licencia.propietario.ci=licCon.propietario.ci");
        List<Persona> listaPer = personas.getResultList();
        if (!listaPer.isEmpty()) {
            for (Persona persona : listaPer) {
                System.out.println("Personas que tienen más de una licencia del mismo tipo emitidas en distintos departamentos: " + persona);
            }
        }
        System.out.println("No hay personas que tengan más de una licencia del mismo tipo emitidas en distintos departamentos");
    }
}
