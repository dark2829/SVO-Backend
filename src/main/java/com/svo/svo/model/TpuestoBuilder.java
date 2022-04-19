package com.svo.svo.model;

public class TpuestoBuilder {
    private TpuestoBuilder(){
    }

    public static TpuestoDTO fromVO(TpuestoVO tpuestoVO){
        TpuestoVO origin = tpuestoVO;
        TpuestoDTO destin = new TpuestoDTO();

        destin.setId(origin.getId());
        destin.setNombre_puesto(origin.getNombre_puesto());
        return destin;
    }

    public static TpuestoVO fromDTO(TpuestoDTO tpuestoDTO){
        TpuestoDTO origin = tpuestoDTO;
        TpuestoVO destin = new TpuestoVO();

        destin.setId(origin.getId());
        destin.setNombre_puesto(origin.getNombre_puesto());
        return destin;
    }
}
