package obligatorio1.db4o.modelo;

import javax.persistence.EntityManager;

/**
 *
 * @author tomas
 */
public class Moto extends Vehiculo {

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
