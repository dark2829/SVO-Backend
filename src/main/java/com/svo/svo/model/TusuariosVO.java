package com.svo.svo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tusuarios")
@NamedQueries({
        //@NamedQuery(name = "TproveedoresVO.findAllProveedores", query = "select p from TproveedoresVO p"),
})
public class TusuariosVO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String correo;
    private String contraseña;
    @ManyToOne
    @JoinColumn(name="tpersonas_id",referencedColumnName = "id")
    private TpersonaVO idPersona;
    //tcompras
    @ManyToOne
    @JoinColumn(name="templeados_id",referencedColumnName = "id")
    private TempleadosVO idEmpleado;
    @ManyToOne
    @JoinColumn(name="trol_id",referencedColumnName = "id")
    private TrolVO idRol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public TpersonaVO getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(TpersonaVO idPersona) {
        this.idPersona = idPersona;
    }

    public TempleadosVO getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(TempleadosVO idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public TrolVO getIdRol() {
        return idRol;
    }

    public void setIdRol(TrolVO idRol) {
        this.idRol = idRol;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
