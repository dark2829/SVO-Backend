package com.svo.svo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;

@Entity
@Table(name="tsolicitudcancelacion")
@NamedQueries({
        //@NamedQuery(name = "TproveedoresVO.findAllProveedores", query = "select p from TproveedoresVO p"),
})
public class TsolicitudCancelacionVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String estatus;
    private String motivo_cancel;
    private String motivo_resp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getMotivo_cancel() {
        return motivo_cancel;
    }

    public void setMotivo_cancel(String motivo_cancel) {
        this.motivo_cancel = motivo_cancel;
    }

    public String getMotivo_resp() {
        return motivo_resp;
    }

    public void setMotivo_resp(String motivo_resp) {
        this.motivo_resp = motivo_resp;
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
