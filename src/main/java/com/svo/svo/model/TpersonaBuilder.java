package com.svo.svo.model;

public class TpersonaBuilder {
    public TpersonaBuilder() {
    }

    public static TpersonaDTO fromVO(TpersonaVO tpersonaVO){
        TpersonaVO origen = tpersonaVO;
        TpersonaDTO destin = new TpersonaDTO();

        destin.setId(origen.getId());
        destin.setFoto(origen.getFoto());
        destin.setNombre(origen.getNombre());
        destin.setApellido_paterno(origen.getApellido_paterno());
        destin.setApellido_materno(origen.getApellido_materno());
        destin.setFecha_nac(origen.getFecha_nac());
        destin.setGenero(origen.getGenero());
        destin.setTelefono(origen.getTelefono());
        destin.setCorreo(origen.getCorreo());
        destin.setDireccion(origen.getDireccion());
        destin.setTarjeta(origen.getTarjeta());
        return destin;
    }

    public static TpersonaVO fromDTO(TpersonaDTO tpersonaDTO){
        TpersonaDTO origen = tpersonaDTO;
        TpersonaVO destin = new TpersonaVO();

        destin.setId(origen.getId());
        destin.setFoto(origen.getFoto());
        destin.setNombre(origen.getNombre());
        destin.setApellido_paterno(origen.getApellido_paterno());
        destin.setApellido_materno(origen.getApellido_materno());
        destin.setFecha_nac(origen.getFecha_nac());
        destin.setGenero(origen.getGenero());
        destin.setTelefono(origen.getTelefono());
        destin.setCorreo(origen.getCorreo());
        destin.setDireccion(origen.getDireccion());
        destin.setTarjeta(origen.getTarjeta());
        return destin;
    }
}
