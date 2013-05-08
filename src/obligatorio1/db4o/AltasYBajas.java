package obligatorio1.db4o;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import obligatorio1.db4o.modelo.*;
import java.util.List;

/**
 *
 * @author tomas
 */
public class AltasYBajas {

    final ObjectContainer db;

    public AltasYBajas(String file) {
        this.db = Db4o.openFile(file);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                db.close();
            }
        }));
    }

    public ObjectContainer getDb() {
        return db;
    }

    public void altaDePersona(Persona p, List<Vehiculo> listaV, List<LicenciaConductor> listaL) {
        List<Persona> resultado = db.queryByExample(new Persona(p.getCi(), null, null));
        if (!resultado.isEmpty()) {
            System.out.println("Ya existe una persona con dicha cedula: " + p.getCi());
        } else {
            for (Vehiculo vehiculo : listaV) {
                p.agregarVehiculo(vehiculo);
            }
            for (LicenciaConductor licenciaConductor : listaL) {
                p.agregarLicencia(licenciaConductor);
            }
            try {
                db.store(p);
                db.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void bajaDeVehiculo(Vehiculo v) {
        try {
            List<Vehiculo> resultado = db.queryByExample(new Vehiculo(v.getMatricula(), null, null, null, null, null));

            switch (resultado.size()) {
                case 0:
                    System.out.println("No existe Vehículo con matrícula: " + v.getMatricula());
                    break;
                case 1:
                    db.delete(resultado.get(0));
                    db.commit();
                    break;
                default:
                    System.out.println("Existe más de un vehículo con matrícula: " + v.getMatricula());
                    for (Vehiculo vehiculo : resultado) {
                        System.out.println(vehiculo);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Persona actualizarLicencias(Persona p) {
        Persona actualizada = null;
        try {
            List<Persona> resultado = db.queryByExample(new Persona(p.getCi(), null, null));
            switch (resultado.size()) {
                case 0:
                    System.out.println("No existe Persona con cédula: " + p.getCi());
                    break;
                case 1:
                    Persona persona = resultado.get(0);
                    persona.setLicenciasDeConducir(p.getLicenciasDeConducir());
                    db.commit();
                    actualizada = persona;
                    break;
                default:
                    System.out.println("Existe más de una Persona con cédula: " + p.getCi());
                    for (Persona p2 : resultado) {
                        System.out.println(p2);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actualizada;
    }
}