package com.svo.svo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="ttarjetas")
@NamedQueries({
        //@NamedQuery(name = "TproveedoresVO.findAllProveedores", query = "select p from TproveedoresVO p"),
})
public class TtarjetasVO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre_propietario;
    private String numero;
    private int cvv;
    @JsonFormat(pattern="dd-MM", timezone = "America/Mexico_City")
    private Date fecha_vencimiento;
    @ManyToOne
    @JoinColumn(name="tpersona_id",referencedColumnName = "id")
    private TpersonaVO idPersona;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_propietario() {
        return nombre_propietario;
    }

    public void setNombre_propietario(String nombre_propietario) {
        this.nombre_propietario = nombre_propietario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public TpersonaVO getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(TpersonaVO idPersona) {
        this.idPersona = idPersona;
    }
}

