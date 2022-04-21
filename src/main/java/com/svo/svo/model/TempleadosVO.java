package com.svo.svo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="templeados")
@NamedQueries({
        //@NamedQuery(name = "TproveedoresVO.findAllProveedores", query = "select p from TproveedoresVO p"),
})
public class TempleadosVO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer no_empleado;
    private String curp;
    private float salario;
    private String estatus;
    @ManyToOne
    @JoinColumn(name="tpersonas_id",referencedColumnName = "id")
    private TpersonaVO idPersona;
    @ManyToOne
    @JoinColumn(name="tpuesto_id",referencedColumnName = "id")
    private TpuestoVO idPuesto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public Integer getNo_empleado() {
        return no_empleado;
    }

    public void setNo_empleado(Integer no_empleado) {
        this.no_empleado = no_empleado;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public String getEstatus() { return estatus; }

    public void setEstatus(String estatus) { this.estatus = estatus; }

    public TpersonaVO getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(TpersonaVO idPersona) {
        this.idPersona = idPersona;
    }

    public TpuestoVO getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(TpuestoVO idPuesto) {
        this.idPuesto = idPuesto;
    }
}
