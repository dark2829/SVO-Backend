package com.svo.svo.model;

public class TcomprasBuilder {
    public TcomprasBuilder() {
    }

    public static TcomprasDTO fromVO(TcomprasVO tcomprasVO){
        TcomprasVO origin = tcomprasVO;
        TcomprasDTO destin = new TcomprasDTO();

        destin.setId(origin.getId());
        destin.setCodigo_compra(origin.getCodigo_compra());
        destin.setPago_total(origin.getPago_total());
        destin.setTipo_envio(origin.getTipo_envio());
        destin.setFecha_venta(origin.getFecha_venta());
        destin.setFacturado(origin.getFacturado());
        destin.setDireccion(origin.getDireccion());
        destin.setIdUsuario(origin.getIdUsuario());
        destin.setCarrito(origin.getCarrito());
        return destin;
    }

    public static TcomprasVO fromDTO(TcomprasDTO tcomprasDTO){
        TcomprasDTO origin = tcomprasDTO;
        TcomprasVO destin = new TcomprasVO();

        destin.setId(origin.getId());
        destin.setCodigo_compra(origin.getCodigo_compra());
        destin.setPago_total(origin.getPago_total());
        destin.setTipo_envio(origin.getTipo_envio());
        destin.setFecha_venta(origin.getFecha_venta());
        destin.setFacturado(origin.getFacturado());
        destin.setDireccion(origin.getDireccion());
        destin.setIdUsuario(origin.getIdUsuario());
        destin.setCarrito(origin.getCarrito());
        return destin;
    }
}
