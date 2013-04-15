/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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

    public Auto() {
    }

    public boolean isConvertible() {
        return convertible;
    }

    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }
}
