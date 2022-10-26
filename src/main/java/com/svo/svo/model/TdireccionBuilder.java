package com.svo.svo.model;

public class TdireccionBuilder {
    public TdireccionBuilder() {
    }

    public static TdireccionDTO fromVO(TdireccionVO tdireccionVO){
        TdireccionVO origen = tdireccionVO;
        TdireccionDTO destin = new TdireccionDTO();

        destin.setId(origen.getId());
        destin.setCalle(origen.getCalle());
        destin.setColonia(origen.getColonia());
        destin.setMunicipio(origen.getMunicipio());
        destin.setEstado(origen.getEstado());
        destin.setCp(origen.getCp());
        destin.setN_interior(origen.getN_interior());
        destin.setN_exterior(origen.getN_exterior());
        destin.setReferencia(origen.getReferencia());

        return destin;
    }

    public static TdireccionVO fromDTO(TdireccionDTO tdireccionDTO){
        TdireccionDTO origen = tdireccionDTO;
        TdireccionVO destin = new TdireccionVO();

        destin.setId(origen.getId());
        destin.setCalle(origen.getCalle());
        destin.setColonia(origen.getColonia());
        destin.setMunicipio(origen.getMunicipio());
        destin.setEstado(origen.getEstado());
        destin.setCp(origen.getCp());
        destin.setN_interior(origen.getN_interior());
        destin.setN_exterior(origen.getN_exterior());
        destin.setReferencia(origen.getReferencia());
        return destin;
    }
}
