package com.svo.svo.model;

public class TproductosBuilder {
    public TproductosBuilder() {
    }

    public static TproductosDTO fromVO(TproductosVO tproductosVO){
        TproductosVO origen = tproductosVO;
        TproductosDTO destin = new TproductosDTO();

        destin.setId(origen.getId());
        destin.setImagen(origen.getImagen());
        destin.setCodigo_prod(origen.getCodigo_prod());
        destin.setNombre(origen.getNombre());
        destin.setCategoria(origen.getCategoria());
        destin.setCantidad(origen.getCantidad());
        destin.setPrecio_compra(origen.getPrecio_compra());
        destin.setPrecio_venta(origen.getPrecio_venta());
        destin.setPrecio_descuento(origen.getPrecio_descuento());
        destin.setDescripcion(origen.getDescripcion());
        destin.setEstatus(origen.getEstatus());
        return destin;
    }

    public static TproductosVO fromDTO(TproductosDTO tproductosDTO){
        TproductosDTO origen = tproductosDTO;
        TproductosVO destin = new TproductosVO();

        destin.setId(origen.getId());
        destin.setImagen(origen.getImagen());
        destin.setCodigo_prod(origen.getCodigo_prod());
        destin.setNombre(origen.getNombre());
        destin.setCategoria(origen.getCategoria());
        destin.setCantidad(origen.getCantidad());
        destin.setPrecio_compra(origen.getPrecio_compra());
        destin.setPrecio_venta(origen.getPrecio_venta());
        destin.setPrecio_descuento(origen.getPrecio_descuento());
        destin.setDescripcion(origen.getDescripcion());
        destin.setEstatus(origen.getEstatus());
        return destin;
    }
}
