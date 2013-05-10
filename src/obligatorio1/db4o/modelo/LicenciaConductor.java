package obligatorio1.db4o.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author tomas
 */
public class LicenciaConductor implements Serializable {

    private int numero;
    private String categoria;
    private Date vencimiento;
    private Persona propietario;
    private String restriccion;
    private Departamento departamento;

    public LicenciaConductor(int numero, String categoria, Date vencimiento, Persona propietario, String restriccion, Departamento departamento) {
        this.numero = numero;
        this.categoria = categoria;
        this.vencimiento = vencimiento;
        this.propietario = propietario;
        this.restriccion = restriccion;
        this.departamento = departamento;
    }

    public LicenciaConductor() {
    }

    public void copy(LicenciaConductor otra) {
        setCategoria(otra.getCategoria());
        setVencimiento(otra.getVencimiento());
//        setPropietario(otra.getPropietario());
        setRestriccion(otra.getRestriccion());
        setDepartamento(otra.getDepartamento());
    }

    public int getNumero() {
        return numero;
    }

    public String getCategoria() {
        return categoria;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Persona getPropietario() {
        return propietario;
    }

    public void setPropietario(Persona propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "LicenciaConductor {" + "numero=" + numero + ", categoria=" + categoria + ", vencimiento=" + vencimiento + ", restriccion=" + restriccion + ", departamento=" + departamento + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.numero;
        hash = 53 * hash + Objects.hashCode(this.categoria);
        hash = 53 * hash + Objects.hashCode(this.vencimiento);
        hash = 53 * hash + Objects.hashCode(this.restriccion);
        hash = 53 * hash + Objects.hashCode(this.departamento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LicenciaConductor other = (LicenciaConductor) obj;
        if (this.numero != other.numero) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.vencimiento, other.vencimiento)) {
            return false;
        }
        if (!Objects.equals(this.restriccion, other.restriccion)) {
            return false;
        }
        if (!Objects.equals(this.departamento, other.departamento)) {
            return false;
        }
        return true;
    }
}
