/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
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
}
