package com.svo.svo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TpagosDTO {
    private Long id;
    private String tipo_pago;
    private String estatus;
    private TcomprasVO idCompra;
    private String tarjetautilizada;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
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

    public String getTarjetautilizada() {
        return tarjetautilizada;
    }

    public void setTarjetautilizada(String tarjetautilizada) {
        this.tarjetautilizada = tarjetautilizada;
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
