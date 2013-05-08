package obligatorio1.db4o;

import java.util.ArrayList;
import obligatorio1.db4o.modelo.Persona;

/**
 *
 * @author tomas
 */
public class DB40 {

    public static void main(String[] args) throws Exception {
        AltasYBajas altasybajas = new AltasYBajas("obligatorio.dbo");
        
        Persona tomas = new Persona(45203730, "Lazaro", "casa");
        Persona jeas = new Persona(12345678, "Ñahui", "apto");

        altasybajas.altaDePersona(tomas, new ArrayList(), new ArrayList());
        altasybajas.altaDePersona(jeas, new ArrayList(), new ArrayList());

        Consultas.consultaLicPer(altasybajas.getDb());
        Consultas.consultaPromedio(altasybajas.getDb());
        
        System.out.println("Personas en base de datos al final;");
        for (Persona persona : altasybajas.db.query(Persona.class)) {
            System.out.println(persona);
        }
    }
}
