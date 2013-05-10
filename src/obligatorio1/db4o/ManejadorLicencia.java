package obligatorio1.db4o;

import com.db4o.ObjectContainer;
import obligatorio1.db4o.modelo.LicenciaConductor;

/**
 *
 * @author tomas
 */
public class ManejadorLicencia extends Manejador<LicenciaConductor, Integer> {

    public ManejadorLicencia(ObjectContainer db) {
        super(db);
    }

    @Override
    protected LicenciaConductor exampleByPk(Integer key) {
        return new LicenciaConductor(key, null, null, null, null, null);
    }
}
