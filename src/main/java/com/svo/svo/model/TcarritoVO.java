package com.svo.svo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tcarrito")
@NamedQueries({
        //@NamedQuery(name = "TproveedoresVO.findAllProveedores", query = "select p from TproveedoresVO p"),
})
public class TcarritoVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private float precio_unitario;
    private float precio_descuento;
    private float precio_total;
    @ManyToOne
    @JoinColumn(name="tusuarios_id",referencedColumnName = "id")
    private TusuariosVO idUsuario;
    @ManyToOne
    @JoinColumn(name="tcompras_id",referencedColumnName = "id")
    private TcomprasVO idCompra;
    @ManyToMany
    @JoinTable(
            name = "tcarrito_has_tproductos",
            joinColumns = @JoinColumn(name = "tcarrito_id"),
            inverseJoinColumns = @JoinColumn(name = "tproductos_id"))
    private List<TproductosVO> producto = new ArrayList<TproductosVO>();

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

    public float getPrecio_descuento() {
        return precio_descuento;
    }

    public void setPrecio_descuento(float precio_descuento) {
        this.precio_descuento = precio_descuento;
    }

    public float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    }

    public TusuariosVO getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TusuariosVO idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TcomprasVO getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(TcomprasVO idCompra) {
        this.idCompra = idCompra;
    }

    public List<TproductosVO> getProducto() {
        return producto;
    }

    public void setProducto(List<TproductosVO> producto) {
        this.producto = producto;
    }
}
