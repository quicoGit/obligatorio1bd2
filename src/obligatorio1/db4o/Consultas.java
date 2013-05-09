package obligatorio1.db4o;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.db4o.query.Query;
import java.util.Arrays;
import java.util.Collections;
import obligatorio1.db4o.modelo.*;

/**
 *
 * @author tomas
 */
public class Consultas {

    private static class Estado {

        private long acumulador = 0;
        private long cuenta = 0;

        public void contar(long dato) {
            acumulador += dato;
            cuenta += 1;
        }

        public double promedio() {
            return cuenta == Double.NaN ? -1 : (acumulador / cuenta);
        }
    }

    public static double consultaPromedio(ObjectContainer em) {
        final Estado estado = new Estado();
        ObjectSet<Persona> personas = em.query(new Predicate<Persona>() {
            @Override
            public boolean match(Persona p) {
                estado.contar(p.getVehiculos().size());
                return false;
            }
        });
        personas.size(); //me aseguro que se ejecute la query

        return estado.promedio();
    }

    public static List<Persona> consultaLicPer(ObjectContainer em) {

        ObjectSet<Persona> listaPer = em.query(new Predicate<Persona>() {
            private Map<String, List<LicenciaConductor>> licenciasPorCategoria = new HashMap<>();
            private Set<Departamento> deptos = new HashSet<>();

            @Override
            public boolean match(Persona p) {
                licenciasPorCategoria.clear();
                for (LicenciaConductor l : p.getLicenciasDeConducir()) {
                    if (!licenciasPorCategoria.containsKey(l.getCategoria())) {
                        licenciasPorCategoria.put(l.getCategoria(), new ArrayList<LicenciaConductor>());
                    }
                    licenciasPorCategoria.get(l.getCategoria()).add(l);
                }

                deptos.clear();
                for (List<LicenciaConductor> list : licenciasPorCategoria.values()) {
                    for (LicenciaConductor licenciaConductor : list) {
                        deptos.add(licenciaConductor.getDepartamento());
                    }
                    if (deptos.size() > 1) {
                        return true;
                    }
                }

                return false;
            }
        });

        return Arrays.asList(listaPer.toArray(new Persona[]{}));
    }
}
