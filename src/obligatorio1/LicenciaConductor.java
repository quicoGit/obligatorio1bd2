/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obligatorio1;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
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
    private String restriccion;   
    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    public LicenciaConductor(int numero, String categoria, Date vencimiento, String restriccion, Departamento departamento) {
        this.numero = numero;
        this.categoria = categoria;
        this.vencimiento = vencimiento;
        this.restriccion = restriccion;
        this.departamento = departamento;
    }

    public LicenciaConductor() {
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

    @Override
    public String toString() {
        return "LicenciaConductor{" + "numero=" + numero + ", categoria=" + categoria + ", vencimiento=" + vencimiento + ", restriccion=" + restriccion + ", departamento=" + departamento + '}';
    }
    
}
