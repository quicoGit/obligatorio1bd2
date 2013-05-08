/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author JÑahui
 */
@Entity
@DiscriminatorColumn(name = "tipo_vehiculo")
@DiscriminatorValue("4")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Vehiculo implements Serializable {

    @Id
    private String matricula;
    private String nroMotor;
    private String nroChasis;
    private String marca;
    private String modelo;
    @ManyToOne
    @JoinColumn(name = "ci_dueño")
    private Persona dueño;

    public Vehiculo(String matricula, String nroMotor, String nroChasis, String marca, String modelo, Persona dueño) {
        this.matricula = matricula;
        this.nroMotor = nroMotor;
        this.nroChasis = nroChasis;
        this.marca = marca;
        this.modelo = modelo;
        this.dueño = dueño;
    }

    /**
     *
     * @param em
     * @param matricula
     * @param nroMotor
     * @param nroChasis
     * @param marca
     * @param modelo
     * @param dueño
     */
    public Vehiculo(EntityManager em,String matricula, String nroMotor, String nroChasis, String marca, String modelo, int dueño) {
        this(matricula, nroMotor, nroChasis, marca, modelo, em.find(Persona.class, dueño));

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
