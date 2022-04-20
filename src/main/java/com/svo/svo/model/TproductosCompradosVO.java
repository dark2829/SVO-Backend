package com.svo.svo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tproductoscomprados")
@NamedQueries({
        //@NamedQuery(name = "TproveedoresVO.findAllProveedores", query = "select p from TproveedoresVO p"),
})
public class TproductosCompradosVO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private float precio_unitario;
    private float precio_total;
    private float precio_descuento;
    @ManyToOne
    @JoinColumn(name="tcompras",referencedColumnName = "id")
    private TcomprasVO idCompra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(float precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    }

    public float getPrecio_descuento() {
        return precio_descuento;
    }

    public void setPrecio_descuento(float precio_descuento) {
        this.precio_descuento = precio_descuento;
    }

    public TcomprasVO getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(TcomprasVO idCompra) {
        this.idCompra = idCompra;
    }
}
