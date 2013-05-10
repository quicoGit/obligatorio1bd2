package obligatorio1.db4o;

import com.db4o.ObjectContainer;
import java.util.List;

/**
 *
 * @author tomas
 */
public abstract class Manejador<T, PK> {

    final ObjectContainer db;

    public Manejador(ObjectContainer db) {
        this.db = db;
    }

    abstract protected T exampleByPk(PK key);

    public T find(PK key) {
        List<T> resultado = db.queryByExample(exampleByPk(key));
        switch (resultado.size()) {
            case 0:
                return null;
            case 1:
                return resultado.get(0);
            default:
                StringBuilder buffer = new StringBuilder("Existe más de un elemento con clave primaria: " + key);
                for (T elemento : resultado) {
                    buffer.append(elemento);
                    buffer.append('\n');
                }
                throw new PersistenciaException(buffer.toString());
        }
    }

    public T get(PK key) {
        T existe = find(key);
        if (existe == null) {
            throw new PersistenciaException("No existe elemento con clave primaria: " + key);
        } else {
            return existe;
        }
    }

    public void verificarInexistente(PK key) {
        T existe = find(key);
        if (existe != null) {
            throw new PersistenciaException("Ya existe un elemento " + existe + " con clave primaria: " + key);
        }
    }
}
