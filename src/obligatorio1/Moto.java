package obligatorio1;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author JÑahui
 */
@Entity
@DiscriminatorValue("2")
public class Moto extends Vehiculo {

    @ManyToOne
    @JoinColumn(name = "id_tipomoto")
    private TipoMoto tipo;

    public Moto(TipoMoto tipo, String matricula, String nroMotor, String nroChasis, String marca, String modelo, Persona dueño) {
        super(matricula, nroMotor, nroChasis, marca, modelo, dueño);
        assert (tipo != null);
        this.tipo = tipo;
    }

    public Moto(EntityManager em, TipoMoto tipo, String matricula, String nroMotor, String nroChasis, String marca, String modelo, int dueño) {
        this(tipo, matricula, nroMotor, nroChasis, marca, modelo, em.find(Persona.class, dueño));
    }

    public Moto() {
    }

    public TipoMoto getTipo() {
        return tipo;
    }

    public void setTipo(TipoMoto tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Vehiculo {" + "matricula=" + this.getMatricula() + ", nroMotor=" + this.getNroMotor() + ", nroChasis=" + this.getNroChasis() + ", marca=" + this.getMarca() + ", modelo=" + this.getModelo() + "Moto {" + "tipo=" + tipo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 29 * hash + Objects.hashCode(this.tipo);
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
        final Moto other = (Moto) obj;
        if (!Objects.equals(this.tipo, other.tipo)) {
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
