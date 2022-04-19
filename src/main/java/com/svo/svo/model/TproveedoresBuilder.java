package com.svo.svo.model;

public class TproveedoresBuilder {
    public TproveedoresBuilder() {
    }

    public static TproveedoresDTO fromVO(TproveedoresVO tproveedoresVO){
        TproveedoresVO origen =tproveedoresVO;
        TproveedoresDTO destin = new TproveedoresDTO();

        destin.setId(origen.getId());
        destin.setNombre(origen.getNombre());
        destin.setTelefono(origen.getTelefono());
        destin.setCorreo(origen.getCorreo());
        destin.setDireccion(origen.getDireccion());
        destin.setProvee(origen.getProvee());
        return destin;
    }

    public static TproveedoresVO fromDTO(TproveedoresDTO tproveedoresDTO){
        TproveedoresDTO origen = tproveedoresDTO;
        TproveedoresVO destin = new TproveedoresVO();

        destin.setId(origen.getId());
        destin.setNombre(origen.getNombre());
        destin.setTelefono(origen.getTelefono());
        destin.setCorreo(origen.getCorreo());
        destin.setDireccion(origen.getDireccion());
        destin.setProvee(origen.getProvee());
        return destin;
    }
}
