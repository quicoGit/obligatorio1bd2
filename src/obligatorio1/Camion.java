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
@DiscriminatorValue("3")
public class Camion extends Vehiculo {

    private int carga;
    private short ejes;

    public Camion(int carga, short ejes, String matricula, String nroMotor, String nroChasis, String marca, String modelo, Persona dueño) {
        super(matricula, nroMotor, nroChasis, marca, modelo, dueño);
        this.carga = carga;
        this.ejes = ejes;
    }
    public Camion(EntityManager em, int carga, short ejes, String matricula, String nroMotor, String nroChasis, String marca, String modelo, int dueño) {
        this(carga, ejes, matricula, nroMotor, nroChasis, marca, modelo, em.find(Persona.class, dueño));       
    }

    public Camion() {
    }

    public int getCarga() {
        return carga;
    }

    public short getEjes() {
        return ejes;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }

    public void setEjes(short ejes) {
        this.ejes = ejes;
    }

    @Override
    public String toString() {
        return "Vehiculo {" + "matricula=" + this.getMatricula() + ", nroMotor=" + this.getNroMotor() + ", nroChasis=" + this.getNroChasis() + ", marca=" + this.getMarca() + ", modelo=" + this.getModelo() + "Camion {" + "carga=" + carga + ", ejes=" + ejes + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.carga;
        hash = 41 * hash + this.ejes;
        hash = 41 * hash + Objects.hashCode(this.matricula);
        hash = 41 * hash + Objects.hashCode(this.nroMotor);
        hash = 41 * hash + Objects.hashCode(this.nroChasis);
        hash = 41 * hash + Objects.hashCode(this.marca);
        hash = 41 * hash + Objects.hashCode(this.modelo);
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
        final Camion other = (Camion) obj;
        if (this.carga != other.carga) {
            return false;
        }
        if (this.ejes != other.ejes) {
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
