package obligatorio1.db4o;

import com.db4o.ObjectContainer;
import obligatorio1.db4o.modelo.Departamento;

/**
 *
 * @author tomas
 */
public class ManejadorDepartamento extends Manejador<Departamento, Integer> {

    public ManejadorDepartamento(ObjectContainer db) {
        super(db);
    }

    @Override
    protected Departamento exampleByPk(Integer key) {
        return new Departamento(key, null);
    }
}
