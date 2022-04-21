package com.svo.svo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TpersonaDTO {
    private Long id;
    private String foto;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    @JsonFormat(pattern="dd-MM-yyyy", timezone = "America/Mexico_City")
    private Date fecha_nac;
    private String genero;
    private String telefono;
    private String correo;
    private List<TdireccionVO> direccion = new ArrayList<TdireccionVO>();
    private List<TtarjetasVO> tarjeta = new ArrayList<TtarjetasVO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<TdireccionVO> getDireccion() {
        return direccion;
    }

    public void setDireccion(List<TdireccionVO> direccion) {
        this.direccion = direccion;
    }

    public List<TtarjetasVO> getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(List<TtarjetasVO> tarjeta) {
        this.tarjeta = tarjeta;
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
