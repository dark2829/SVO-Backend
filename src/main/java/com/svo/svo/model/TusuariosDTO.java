package com.svo.svo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TusuariosDTO {
    private Long id;
    private String correo;
    private String contraseña;
    private TpersonaVO idPersona;
    private TempleadosVO idEmpleado;
    private TrolVO idRol;
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
