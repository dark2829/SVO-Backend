package com.svo.svo.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TfavoritosDTO {
    private Long id;
    private TpedidosVO idProducto;
    private TusuariosVO idUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TpedidosVO getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(TpedidosVO idProducto) {
        this.idProducto = idProducto;
    }

    public TusuariosVO getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TusuariosVO idUsuario) {
        this.idUsuario = idUsuario;
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
