package com.svo.svo.model;

public class TproductosCompradosBuilder {
    public TproductosCompradosBuilder() {
    }

    public static TproductosCompradosDTO fromVO(TproductosCompradosVO tproductosCompradosVO) {
        TproductosCompradosVO origen = tproductosCompradosVO;
        TproductosCompradosDTO destin = new TproductosCompradosDTO();

        destin.setId(origen.getId());
        destin.setCantidad(origen.getCantidad());
        destin.setPrecio_unitario(origen.getPrecio_unitario());
        destin.setPrecio_total(origen.getPrecio_total());
        destin.setPrecio_descuento(origen.getPrecio_descuento());
        destin.setIdCompra(origen.getIdCompra());
        destin.setIdProducto(origen.getIdProducto());
        return destin;
    }

    public static TproductosCompradosVO fromDTO(TproductosCompradosDTO tproductosCompradosDTO) {
        TproductosCompradosDTO origen = tproductosCompradosDTO;
        TproductosCompradosVO destin = new TproductosCompradosVO();

        destin.setId(origen.getId());
        destin.setCantidad(origen.getCantidad());
        destin.setPrecio_unitario(origen.getPrecio_unitario());
        destin.setPrecio_total(origen.getPrecio_total());
        destin.setPrecio_descuento(origen.getPrecio_descuento());
        destin.setIdCompra(origen.getIdCompra());
        destin.setIdProducto(origen.getIdProducto());
        return destin;

    }
}