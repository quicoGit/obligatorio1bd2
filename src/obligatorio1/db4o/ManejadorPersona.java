package obligatorio1.db4o;

import com.db4o.ObjectContainer;
import obligatorio1.db4o.modelo.Persona;

/**
 *
 * @author tomas
 */
public class ManejadorPersona extends Manejador<Persona, Integer> {

    public ManejadorPersona(ObjectContainer db) {
        super(db);
    }

    @Override
    protected Persona exampleByPk(Integer key) {
        return new Persona(key, null, null);
    }
}
