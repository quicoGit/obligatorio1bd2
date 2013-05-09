package obligatorio1;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

/**
 *
 * @author JÑahui
 */
@Entity
@DiscriminatorValue("1")
public class Auto extends Vehiculo {

    private boolean convertible;

    public Auto(boolean convertible, String matricula, String nroMotor, String nroChasis, String marca, String modelo, Persona dueño) {
        super(matricula, nroMotor, nroChasis, marca, modelo, dueño);
        this.convertible = convertible;
    }

    public Auto(EntityManager em, boolean convertible, String matricula, String nroMotor, String nroChasis, String marca, String modelo, int dueño) {
        this(convertible, matricula, nroMotor, nroChasis, marca, modelo, em.find(Persona.class, dueño));

    }

    public Auto() {
    }

    public boolean isConvertible() {
        return convertible;
    }

    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }

    @Override
    public String toString() {
        return "Vehiculo {" + "matricula=" + this.getMatricula() + ", nroMotor=" + this.getNroMotor() + ", nroChasis=" + this.getNroChasis() + ", marca=" + this.getMarca() + ", modelo=" + this.getModelo() + "Auto {" + "convertible=" + convertible + '}' + '}';
    }

    @Override
    public int hashCode() {
        int hash = 11;
        hash = 29 * hash + (this.convertible ? 1 : 0);
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
        final Auto other = (Auto) obj;
        if (this.convertible != other.convertible) {
            return false;
        }
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
