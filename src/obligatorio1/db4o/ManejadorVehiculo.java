package obligatorio1.db4o;

import com.db4o.ObjectContainer;
import obligatorio1.db4o.modelo.Vehiculo;

/**
 *
 * @author tomas
 */
public class ManejadorVehiculo extends Manejador<Vehiculo, String> {

    public ManejadorVehiculo(ObjectContainer db) {
        super(db);
    }

    @Override
    protected Vehiculo exampleByPk(String key) {
        return new Vehiculo(key, null, null, null, null, null);
    }
    
}
