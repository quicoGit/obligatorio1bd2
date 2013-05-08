package obligatorio1.db4o.modelo;

import java.io.Serializable;

/**
 *
 * @author tomas
 */
public class Departamento implements Serializable {

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
