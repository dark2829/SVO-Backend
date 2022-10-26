package com.svo.svo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class TpedidosDTO {
    private Long id;
    private Date fecha_entrega;
    private String estatus;
    private TcomprasVO idCompra;
    private TsolicitudCancelacionVO solicitudCancelacion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public TcomprasVO getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(TcomprasVO idCompra) {
        this.idCompra = idCompra;
    }

    public TsolicitudCancelacionVO getSolicitudCancelacion() {
        return solicitudCancelacion;
    }

    public void setSolicitudCancelacion(TsolicitudCancelacionVO solicitudCancelacion) {
        this.solicitudCancelacion = solicitudCancelacion;
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
