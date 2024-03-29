package obligatorio1.db4o.modelo;

import java.io.Serializable;

/**
 *
 * @author tomas
 */
public class Vehiculo implements Serializable {

    private String matricula;
    private String nroMotor;
    private String nroChasis;
    private String marca;
    private String modelo;
    private Persona dueño;

    public Vehiculo(String matricula, String nroMotor, String nroChasis, String marca, String modelo, Persona dueño) {
        this.matricula = matricula;
        this.nroMotor = nroMotor;
        this.nroChasis = nroChasis;
        this.marca = marca;
        this.modelo = modelo;
        this.dueño = dueño;
    }

    public Vehiculo() {
    }

    @Override
    public String toString() {
        return "Vehiculo {" + "matricula=" + matricula + ", nroMotor=" + nroMotor + ", nroChasis=" + nroChasis + ", marca=" + marca + ", modelo=" + modelo + '}';
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNroMotor() {
        return nroMotor;
    }

    public String getNroChasis() {
        return nroChasis;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Persona getDueño() {
        return dueño;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNroMotor(String nroMotor) {
        this.nroMotor = nroMotor;
    }

    public void setNroChasis(String nroChasis) {
        this.nroChasis = nroChasis;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setDueño(Persona dueño) {
        this.dueño = dueño;
    }
}
