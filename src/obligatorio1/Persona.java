package obligatorio1;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author JÑahui
 */
@Entity
public class Persona implements Serializable {

    @Id
    private int ci;
    private String apellido;
    private String domicilio;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dueño", cascade = CascadeType.PERSIST)
    private Set<Vehiculo> vehiculos;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "propietario", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<LicenciaConductor> licenciasDeConducir;

    public Persona(int ci, String apellido, String domicilio) {
        this.ci = ci;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.licenciasDeConducir = new HashSet<>();
        this.vehiculos = new HashSet<>();
    }

    public Persona() {
    }

    public void agregarVehiculo(Vehiculo v) {
        if (v != null) {
            vehiculos.add(v);
        }
    }

    public void agregarLicencia(LicenciaConductor l) {
        if (l != null) {
            licenciasDeConducir.add(l);
        }
    }

    public boolean removerLicencia(LicenciaConductor l) {
        return licenciasDeConducir.remove(l);
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
