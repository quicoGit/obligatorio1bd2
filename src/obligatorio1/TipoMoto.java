package obligatorio1;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author JÑahui
 */
@Entity
public class TipoMoto implements Serializable {

    @Id
    private int id;
    private String descripcion;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipo")
    private Set<Moto> motos;

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

    public Set<Moto> getMotos() {
        return motos;
    }

    public void setMotos(Set<Moto> motos) {
        this.motos = motos;
    }

    @Override
    public String toString() {
        return "TipoMoto{" + "id=" + id + ", descripcion=" + descripcion;
    }
    
}
