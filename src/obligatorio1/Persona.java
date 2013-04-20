/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dueño", cascade = CascadeType.ALL)//atributo de vehiculo-indica la relacion inversa
    private Set<Vehiculo> vehiculos;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "propietario", cascade = CascadeType.ALL)//Defines the set of cascadable
    //operations that are propagated to the associated entity
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
