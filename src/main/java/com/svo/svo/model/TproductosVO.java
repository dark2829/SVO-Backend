package com.svo.svo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tproductos")
@NamedQueries({
        @NamedQuery(name = "TproductosVO.findAllProductos", query = "select p from TproductosVO p"),
        @NamedQuery(name = "TproductosVO.findStockBajo", query = "select p from TproductosVO p where p.cantidad<=5"),
        @NamedQuery(name = "TproductosVO.busquedaProductos", query = "select p from TproductosVO p where p.nombre like: nombre or p.categoria like: nombre or p.descripcion like: nombre"),
        @NamedQuery(name = "TproductosVO.findProductoByCodigoProducto", query = "select p from TproductosVO p where p.codigo_prod =: codProducto"),

})
public class TproductosVO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] imagen;
    private String codigo_prod;
    private String nombre;
    private String categoria;
    private int cantidad;
    private float precio_compra;
    private float precio_venta;
    private float precio_descuento;
    private String descripcion;
    private String estatus;
    private int contactado;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getCodigo_prod() {
        return codigo_prod;
    }

    public void setCodigo_prod(String codigo_prod) {
        this.codigo_prod = codigo_prod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(float precio_compra) {
        this.precio_compra = precio_compra;
    }

    public float getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(float precio_venta) {
        this.precio_venta = precio_venta;
    }

    public float getPrecio_descuento() {
        return precio_descuento;
    }

    public void setPrecio_descuento(float precio_descuento) {
        this.precio_descuento = precio_descuento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getContactado() {
        return contactado;
    }

    public void setContactado(int contactado) {
        this.contactado = contactado;
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
