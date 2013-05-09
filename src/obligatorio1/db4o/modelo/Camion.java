package obligatorio1.db4o.modelo;

/**
 *
 * @author tomas
 */
public class Camion extends Vehiculo {

    private int carga;
    private short ejes;

    public Camion(int carga, short ejes, String matricula, String nroMotor, String nroChasis, String marca, String modelo, Persona dueño) {
        super(matricula, nroMotor, nroChasis, marca, modelo, dueño);
        this.carga = carga;
        this.ejes = ejes;
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
}
