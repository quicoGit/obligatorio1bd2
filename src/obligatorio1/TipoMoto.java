/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author JÑahui
 */
@Entity
public class TipoMoto implements Serializable {

    @Id
    private int id;
    private String descripcion;

    public TipoMoto(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public TipoMoto() {
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
