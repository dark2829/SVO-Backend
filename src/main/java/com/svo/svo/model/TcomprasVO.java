package com.svo.svo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="tcompras")
@NamedQueries({
        //@NamedQuery(name = "TproveedoresVO.findAllProveedores", query = "select p from TproveedoresVO p"),
})
public class TcomprasVO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo_compra;
    private float pago_total;
    private String tipo_envio;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd-MM-yyyy", timezone = "America/Mexico_City")
    private Date fecha_venta;
    private int facturado;
    private String direccion;
    @ManyToOne
    @JoinColumn(name="tusuarios_id",referencedColumnName = "id")
    private TusuariosVO idUsuario;
    @ManyToMany
    @JoinTable(
            name = "tcarrito_has_tcompras",
            joinColumns = @JoinColumn(name = "tcompras_id"),
            inverseJoinColumns = @JoinColumn(name = "tcarrito_id"))
    private List<TcarritoVO> carrito = new ArrayList<TcarritoVO>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo_compra() {
        return codigo_compra;
    }

    public void setCodigo_compra(String codigo_compra) {
        this.codigo_compra = codigo_compra;
    }

    public float getPago_total() {
        return pago_total;
    }

    public void setPago_total(float pago_total) {
        this.pago_total = pago_total;
    }

    public String getTipo_envio() {
        return tipo_envio;
    }

    public void setTipo_envio(String tipo_envio) {
        this.tipo_envio = tipo_envio;
    }

    public Date getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(Date fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public int getFacturado() {
        return facturado;
    }

    public void setFacturado(int facturado) {
        this.facturado = facturado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TusuariosVO getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TusuariosVO idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<TcarritoVO> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<TcarritoVO> carrito) {
        this.carrito = carrito;
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
