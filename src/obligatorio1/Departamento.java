package obligatorio1;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author JÑahui
 */
@Entity
public class Departamento implements Serializable {

    @Id
    private int id;
    private String nombre;

    public Departamento(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Departamento() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Departamento {" + "id=" + id + ", nombre=" + nombre + '}';
    }
}
