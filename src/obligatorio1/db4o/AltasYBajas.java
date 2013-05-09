package obligatorio1.db4o;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import java.util.ArrayList;
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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        db.close();
    }

    public ObjectContainer getDb() {
        return db;
    }

    public Persona findPersona(int ci) {
        List<Persona> resultado = db.queryByExample(new Persona(ci, null, null));
        switch (resultado.size()) {
            case 0:
                return null;
            case 1:
                return resultado.get(0);
            default:
                StringBuilder buffer = new StringBuilder("Existe más de una Persona con la cédula: " + ci);
                for (Persona persona : resultado) {
                    buffer.append(persona);
                    buffer.append('\n');
                }
                throw new PersistenciaException(buffer.toString());
        }
    }

    public Persona getPersona(int ci) {
        Persona existe = findPersona(ci);
        if (existe == null) {
            throw new PersistenciaException("No existe una persona con dicha cedula: " + ci);
        } else {
            return existe;
        }
    }

    public void verificarPersonaInexistente(int ci) {
        if (findPersona(ci) != null) {
            throw new PersistenciaException("Ya existe una persona con dicha cedula: " + ci);
        }
    }

    public void bajaDePersona(Persona p) {
        Persona persona = getPersona(p.getCi());
        try {
            db.delete(persona);
            db.commit();
        } catch (Db4oIOException | DatabaseClosedException | DatabaseReadOnlyException e) {
            db.rollback();
            throw new PersistenciaException(e);
        }
    }

    public void altaDePersona(Persona p, List<Vehiculo> listaV, List<LicenciaConductor> listaL) {
        verificarPersonaInexistente(p.getCi());

        if (listaV != null) {
            for (Vehiculo vehiculo : listaV) {
                p.agregarVehiculo(vehiculo);
            }
        }

        if (listaL != null) {
            for (LicenciaConductor licenciaConductor : listaL) {
                p.agregarLicencia(licenciaConductor);
            }
        }

        try {
            db.store(p);
            db.commit();
        } catch (DatabaseClosedException | DatabaseReadOnlyException | Db4oIOException e) {
            db.rollback();
            throw new PersistenciaException(e);
        }
    }

    public Vehiculo findVehiculo(String matricula) {
        List<Vehiculo> resultado = db.queryByExample(new Vehiculo(matricula, null, null, null, null, null));
        switch (resultado.size()) {
            case 0:
                return null;
            case 1:
                return resultado.get(0);
            default:
                StringBuilder buffer = new StringBuilder("Existe más de un Vehiculo con la matrícula: " + matricula);
                for (Vehiculo vehiculo : resultado) {
                    buffer.append(vehiculo);
                    buffer.append('\n');
                }
                throw new PersistenciaException(buffer.toString());
        }
    }

    public Vehiculo getVehiculo(String matricula) {
        Vehiculo existe = findVehiculo(matricula);
        if (existe == null) {
            throw new PersistenciaException("No existe Vehículo con matrícula: " + matricula);
        } else {
            return existe;
        }
    }

    public void bajaDeVehiculo(Vehiculo v) {
        Vehiculo existe = getVehiculo(v.getMatricula());

        try {
            db.delete(existe);
            db.commit();
        } catch (Db4oIOException | DatabaseClosedException | DatabaseReadOnlyException e) {
            db.rollback();
            throw new PersistenciaException(e);
        }
    }

    public Persona actualizarLicencias(Persona p) {
        Persona persona = getPersona(p.getCi());

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

        try {
            for (LicenciaConductor licenciaConductor : eliminar) {
                persona.removerLicencia(licenciaConductor);
                db.delete(licenciaConductor);
            }

            for (LicenciaConductor nueva : p.getLicenciasDeConducir()) {
                if (!persona.getLicenciasDeConducir().contains(nueva)) {
                    persona.agregarLicencia(nueva);
                }
            }

            db.store(persona);
            db.commit();
        } catch (Db4oIOException | DatabaseClosedException | DatabaseReadOnlyException e) {
            db.rollback();
            throw new PersistenciaException(e);
        }
        return persona;
    }
}