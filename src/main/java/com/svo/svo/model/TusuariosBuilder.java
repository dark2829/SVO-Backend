package com.svo.svo.model;

public class TusuariosBuilder {
    public TusuariosBuilder() {
    }
    public static TusuariosDTO fromVO(TusuariosVO tusuariosVO){
        TusuariosVO origen = tusuariosVO;
        TusuariosDTO destin = new TusuariosDTO();

        destin.setId(origen.getId());
        destin.setCorreo(origen.getCorreo());
        destin.setContraseña(origen.getContraseña());
        destin.setIdPersona(origen.getIdPersona());
        destin.setIdEmpleado(origen.getIdEmpleado());
        destin.setIdRol(origen.getIdRol());
        destin.setProductosFavoritos(origen.getProductosFavoritos());
        return destin;
    }

    public static TusuariosVO fromDTO(TusuariosDTO tusuariosDTO){
        TusuariosDTO origen = tusuariosDTO;
        TusuariosVO destin = new TusuariosVO();

        destin.setId(origen.getId());
        destin.setCorreo(origen.getCorreo());
        destin.setContraseña(origen.getContraseña());
        destin.setIdPersona(origen.getIdPersona());
        destin.setIdEmpleado(origen.getIdEmpleado());
        destin.setIdRol(origen.getIdRol());
        destin.setProductosFavoritos(origen.getProductosFavoritos());
        return destin;
    }

}
