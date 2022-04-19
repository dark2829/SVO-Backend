package com.svo.svo.model;

public class TrolBuilder {
    public TrolBuilder() {
    }

    public static TrolDTO fromVO(TrolVO trolVO){
        TrolVO origen = trolVO;
        TrolDTO destin = new TrolDTO();

        destin.setId(origen.getId());
        destin.setTipo(origen.getTipo());
        return destin;
    }

    public static TrolVO fromVO(TrolDTO trolDTO){
        TrolDTO origen = trolDTO;
        TrolVO destin = new TrolVO();

        destin.setId(origen.getId());
        destin.setTipo(origen.getTipo());
        return destin;
    }
}
