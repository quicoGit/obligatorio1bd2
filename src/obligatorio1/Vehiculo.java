package obligatorio1;

import java.io.Serializable;
import java.util.Objects;
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
    protected String matricula;
    protected String nroMotor;
    protected String nroChasis;
    protected String marca;
    protected String modelo;
    @ManyToOne
    @JoinColumn(name = "ci_dueño")
    protected Persona dueño;

    public Vehiculo(String matricula, String nroMotor, String nroChasis, String marca, String modelo, Persona dueño) {
        assert (dueño != null);
        assert (matricula != null);

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
    public Vehiculo(EntityManager em, String matricula, String nroMotor, String nroChasis, String marca, String modelo, int dueño) {
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.matricula);
        hash = 29 * hash + Objects.hashCode(this.nroMotor);
        hash = 29 * hash + Objects.hashCode(this.nroChasis);
        hash = 29 * hash + Objects.hashCode(this.marca);
        hash = 29 * hash + Objects.hashCode(this.modelo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vehiculo other = (Vehiculo) obj;
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        if (!Objects.equals(this.nroMotor, other.nroMotor)) {
            return false;
        }
        if (!Objects.equals(this.nroChasis, other.nroChasis)) {
            return false;
        }
        if (!Objects.equals(this.marca, other.marca)) {
            return false;
        }
        if (!Objects.equals(this.modelo, other.modelo)) {
            return false;
        }
        return true;
    }
}
