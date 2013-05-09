package obligatorio1.db4o.modelo;

/**
 *
 * @author tomas
 */
public class Auto extends Vehiculo {

    private boolean convertible;

    public Auto(boolean convertible, String matricula, String nroMotor, String nroChasis, String marca, String modelo, Persona dueño) {
        super(matricula, nroMotor, nroChasis, marca, modelo, dueño);
        this.convertible = convertible;
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
}
