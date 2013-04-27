/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author JÑahui
 */
@Entity
public class LicenciaConductor implements Serializable {

    @Id
    private int numero;
    private String categoria;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vencimiento;
    @ManyToOne
    @JoinColumn(name = "ci_propietario")
    private Persona propietario;
    private String restriccion;
    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    public LicenciaConductor(int numero, String categoria, Date vencimiento, Persona propietario, String restriccion, Departamento departamento) {
        this.numero = numero;
        this.categoria = categoria;
        this.vencimiento = vencimiento;
        this.propietario = propietario;
        this.restriccion = restriccion;
        this.departamento = departamento;
    }

    public LicenciaConductor(EntityManager em, int numero, String categoria, Date vencimiento, int propietario, String restriccion, int departamento) {
        this(numero, categoria, vencimiento, em.find(Persona.class, propietario), restriccion, em.find(Departamento.class, departamento));
    }

    public LicenciaConductor() {
    }

    public void copy(LicenciaConductor otra) {
        setCategoria(otra.getCategoria());
        setVencimiento(otra.getVencimiento());
//        setPropietario(otra.getPropietario());
        setRestriccion(otra.getRestriccion());
//        setDepartamento(otra.getDepartamento());
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
        return hash;
    }

    /**
     * Equals segun PK
     */
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
        return true;
    }
}
