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
        destin.setIdCompra(origen.getIdCompra());
        destin.setIdUsuario(origen.getIdUsuario());
        destin.setPersona(origen.getPersona());
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
        destin.setIdCompra(origen.getIdCompra());
        destin.setIdUsuario(origen.getIdUsuario());
        destin.setPersona(origen.getPersona());
        return destin;
    }
}
