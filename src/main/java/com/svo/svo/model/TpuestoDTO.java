package com.svo.svo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TpuestoDTO {
    private Integer id;
    private String nombre_puesto;

    public String getNombre_puesto() { return nombre_puesto; }

    public void setNombre_puesto(String nombre_puesto) { this.nombre_puesto = nombre_puesto; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

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
