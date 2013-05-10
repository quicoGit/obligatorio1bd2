package obligatorio1.db4o.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author tomas
 */
public class Persona implements Serializable {

    private int ci;
    private String apellido;
    private String domicilio;
    private Set<Vehiculo> vehiculos;
    private Set<LicenciaConductor> licenciasDeConducir;

    public Persona(int ci, String apellido, String domicilio) {
        this.ci = ci;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.licenciasDeConducir = new HashSet<LicenciaConductor>();
        this.vehiculos = new HashSet<Vehiculo>();
    }

    public Persona() {
    }

    public boolean agregarVehiculo(Vehiculo v) {
        boolean resultado = false;
        if (v != null) {
            resultado = vehiculos.add(v);
        }
        return resultado;
    }

    public boolean agregarLicencia(LicenciaConductor l) {
        boolean resultado = false;
        if (l != null) {
            resultado = licenciasDeConducir.add(l);
        }
        return resultado;
    }

    public boolean removerLicencia(LicenciaConductor l) {
        return licenciasDeConducir.remove(l);
    }

    public boolean removerVehiculo(Vehiculo v) {
        return vehiculos.remove(v);
    }

    public Set<LicenciaConductor> getLicenciasDeConducir() {
        return licenciasDeConducir;
    }

    public void setLicenciasDeConducir(Set<LicenciaConductor> licenciasDeConducir) {
        this.licenciasDeConducir = licenciasDeConducir;
    }

    public Set<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(Set<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public int getCi() {
        return ci;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Persona {" + "ci=" + ci + ", apellido=" + apellido + ", domicilio=" + domicilio + '}';
    }
}
