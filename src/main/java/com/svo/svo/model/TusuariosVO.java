package com.svo.svo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tusuarios")
@NamedQueries({
        @NamedQuery(name = "TusuariosVO.findIdEmpleadoByIdUser", query = "select u from TusuariosVO u where u.id =:id"),
        @NamedQuery(name = "TusuariosVO.findUserByCorreo", query = "select u from TusuariosVO u where (u.correo =:correo) and u.contraseña =: contrasena"),
        @NamedQuery(name = "TusuariosVO.findUserById", query = "select u from TusuariosVO u where u.id =: id"),
        @NamedQuery(name = "TusuariosVO.findCorreo", query = "select u from TusuariosVO u where u.correo =: correo"),
        @NamedQuery(name = "TusuariosVO.verificarContraseña", query = "select u from TusuariosVO u where u.correo =: correo"),
        @NamedQuery(name = "TusuariosVO.findNoEmpleado", query = "select u from TusuariosVO u where u.idEmpleado.no_empleado =: noEmpleado"),
        @NamedQuery(name = "TusuariosVO.findByNoEmpleado", query = "select u from TusuariosVO u where u.idEmpleado.no_empleado =: noEmpleado"),
        @NamedQuery(name = "TusuariosVO.findUserByIdEmpleado", query = "select u from TusuariosVO u where u.idEmpleado.id =: idEmpleado"),



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
    @ManyToMany
    @JoinTable(
            name = "tfavoritos",
            joinColumns = @JoinColumn(name = "tusuarios_id"),
            inverseJoinColumns = @JoinColumn(name = "tproductos_id"))
    private List<TproductosVO> productosFavoritos = new ArrayList<TproductosVO>();



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

    public List<TproductosVO> getProductosFavoritos() {
        return productosFavoritos;
    }

    public void setProductosFavoritos(List<TproductosVO> productosFavoritos) {
        this.productosFavoritos = productosFavoritos;
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
