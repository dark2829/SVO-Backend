package com.svo.svo.model;

public class TcarritoBuilder {
    public TcarritoBuilder() {
    }

    public static TcarritoDTO fromVO(TcarritoVO tcarritoVO){
        TcarritoVO origen = tcarritoVO;
        TcarritoDTO destin = new TcarritoDTO();

        destin.setId(origen.getId());
        destin.setCantidad(origen.getCantidad());
        destin.setPrecio_unitario(origen.getPrecio_unitario());
        destin.setPrecio_total(origen.getPrecio_total());
        destin.setPrecio_descuento(origen.getPrecio_descuento());
        destin.setIdProducto(origen.getIdProducto());
        destin.setIdUsuario(origen.getIdUsuario());

        return destin;
    }

    public static TcarritoVO fromDTO(TcarritoDTO tcarritoDTO){
        TcarritoDTO origen = tcarritoDTO;
        TcarritoVO destin = new TcarritoVO();

        destin.setId(origen.getId());
        destin.setCantidad(origen.getCantidad());
        destin.setPrecio_unitario(origen.getPrecio_unitario());
        destin.setPrecio_total(origen.getPrecio_total());
        destin.setPrecio_descuento(origen.getPrecio_descuento());
        destin.setIdProducto(origen.getIdProducto());
        destin.setIdUsuario(origen.getIdUsuario());


        return destin;
    }
}
