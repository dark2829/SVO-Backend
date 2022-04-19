package com.svo.svo.model;

public class TpagosBuilder {
    public TpagosBuilder() {
    }

    public static TpagosDTO fromVO(TpagosVO tpagosVO){
        TpagosVO origen = tpagosVO;
        TpagosDTO destin = new TpagosDTO();

        destin.setId(origen.getId());
        destin.setTipo_pago(origen.getTipo_pago());
        destin.setEstatus(origen.getEstatus());
        destin.setIdCompra(origen.getIdCompra());
        return destin;
    }

    public static TpagosVO fromDTO(TpagosDTO tpagosDTO){
        TpagosDTO origen = tpagosDTO;
        TpagosVO destin = new TpagosVO();

        destin.setId(origen.getId());
        destin.setTipo_pago(origen.getTipo_pago());
        destin.setEstatus(origen.getEstatus());
        destin.setIdCompra(origen.getIdCompra());
        return destin;
    }
}
