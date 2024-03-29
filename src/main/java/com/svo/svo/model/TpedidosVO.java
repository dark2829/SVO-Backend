package com.svo.svo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="tpedidos")
@NamedQueries({
        @NamedQuery(name = "TpedidosVO.buscarTodosPedidos", query = "select p from TpedidosVO p order by p.fecha_entrega asc "),
        @NamedQuery(name = "TpedidosVO.buscarPedidoPorIdCompra", query = "select p from TpedidosVO p where p.idCompra.id=: idCompra"),
        @NamedQuery(name = "TpedidosVO.buscarPedidoPorId", query = "select p from TpedidosVO p where p.id=: idPedido"),
        @NamedQuery(name = "TpedidosVO.buscarPedidosPorIdUsuario", query = "select p from TpedidosVO p where p.idCompra.idUsuario.id=: idUsuario and p.estatus=: estatusPedido"),
        @NamedQuery(name = "TpedidosVO.buscarPedidoPorCodigoCompra", query = "select p from TpedidosVO p where p.idCompra.codigo_compra like :codigoCompra"),
        @NamedQuery(name = "TpedidosVO.buscarCompraPorFecha", query = "select p from TpedidosVO p where (p.idCompra.idUsuario.id =: idUsuario and p.estatus =: estatusPedido) and (p.fecha_entrega =: fecha or p.idCompra.fecha_venta =: fecha)"),





})
public class TpedidosVO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fecha_entrega;
    private String estatus;
    @ManyToOne
    @JoinColumn(name="tcompras_id",referencedColumnName = "id")
    private TcomprasVO idCompra;
    @ManyToOne
    @JoinColumn(name="tsolicitudcancelacion_id",referencedColumnName = "id")
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
