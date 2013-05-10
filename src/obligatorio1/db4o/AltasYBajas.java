package obligatorio1.db4o;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import java.util.ArrayList;
import java.util.List;
import obligatorio1.db4o.modelo.*;

/**
 *
 * @author tomas
 */
public class AltasYBajas {

    final ObjectContainer db;
    final ManejadorPersona personas;
    final ManejadorVehiculo vehiculos;
    final ManejadorLicencia licencias;
    final ManejadorDepartamento departamentos;

    public AltasYBajas(String file) {
        this.db = Db4o.openFile(file);

        personas = new ManejadorPersona(db);
        vehiculos = new ManejadorVehiculo(db);
        licencias = new ManejadorLicencia(db);
        departamentos = new ManejadorDepartamento(db);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                db.close();
            }
        }));
    }

    public ManejadorPersona getPersonas() {
        return personas;
    }

    public ManejadorVehiculo getVehiculos() {
        return vehiculos;
    }

    public ManejadorLicencia getLicencias() {
        return licencias;
    }

    public ManejadorDepartamento getDepartamentos() {
        return departamentos;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        db.close();
    }

    public ObjectContainer getDb() {
        return db;
    }

    public void bajaDePersona(Persona p) {
        Persona persona = personas.get(p.getCi());
        try {
            for (LicenciaConductor licenciaConductor : persona.getLicenciasDeConducir()) {
                db.delete(licenciaConductor);
            }
            for (Vehiculo vehiculo : persona.getVehiculos()) {
                vehiculo.setDueño(null);
                db.store(vehiculo);
            }
            db.delete(persona);
            db.commit();
        } catch (Db4oIOException | DatabaseClosedException | DatabaseReadOnlyException e) {
            db.rollback();
            throw new PersistenciaException(e);
        }
    }

    public void altaDePersona(Persona p, List<Vehiculo> listaV, List<LicenciaConductor> listaL) {
        personas.verificarInexistente(p.getCi());

        if (listaV != null) {
            for (Vehiculo vehiculo : listaV) {
                if (!vehiculo.getDueño().equals(p)) {
                    throw new PersistenciaException("Vehiculo " + vehiculo + " con dueño " + vehiculo.getDueño() + " es distinto de " + p + ".");
                }
                vehiculos.verificarInexistente(vehiculo.getMatricula());
                p.agregarVehiculo(vehiculo);
            }
        }

        if (listaL != null) {
            for (LicenciaConductor licencia : listaL) {
                agregarLicencia(p, licencia);
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

    public void bajaDeVehiculo(Vehiculo v) {
        Vehiculo existe = vehiculos.get(v.getMatricula());

        try {
            existe.getDueño().removerVehiculo(existe);
            db.store(existe.getDueño());
            db.delete(existe);
            db.commit();
        } catch (Db4oIOException | DatabaseClosedException | DatabaseReadOnlyException e) {
            db.rollback();
            throw new PersistenciaException(e);
        }
    }

    private void agregarLicencia(Persona p, LicenciaConductor l) {
        if (!l.getPropietario().equals(p)) {
            throw new PersistenciaException("Licencia " + l + " con propietario " + l.getPropietario() + " es distinto de " + p + ".");
        }
        licencias.verificarInexistente(l.getNumero());
        p.agregarLicencia(l);
    }

    public Persona actualizarLicencias(Persona p) {
        Persona persona = personas.get(p.getCi());

        List<LicenciaConductor> eliminar = new ArrayList<>();
        for (LicenciaConductor vieja : persona.getLicenciasDeConducir()) {
            boolean remover = true;
            for (LicenciaConductor nueva : p.getLicenciasDeConducir()) {
                if (nueva.equals(vieja)) {
                    remover = false; // Se mantiene igual
                    break;
                } else if (nueva.getNumero() == vieja.getNumero()) {
                    // Hay que actualizar
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
                    nueva.setPropietario(persona);
                    agregarLicencia(persona, nueva);
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