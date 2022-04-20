package com.svo.svo.model;

public class TpedidosBuilder {
    public TpedidosBuilder() {
    }
    public static TpedidosDTO fromVO(TpedidosVO tpedidosVO){
        TpedidosVO origin = tpedidosVO;
        TpedidosDTO destin = new TpedidosDTO();

        destin.setId(origin.getId());
        destin.setFecha_entrega(origin.getFecha_entrega());
        destin.setEstatus(origin.getEstatus());
        destin.setIdCompra(origin.getIdCompra());
        return destin;
    }

    public static TpedidosVO fromDTO(TpedidosDTO tpedidosDTO){
        TpedidosDTO origin = tpedidosDTO;
        TpedidosVO destin = new TpedidosVO();

        destin.setId(origin.getId());
        destin.setFecha_entrega(origin.getFecha_entrega());
        destin.setEstatus(origin.getEstatus());
        destin.setIdCompra(origin.getIdCompra());
        return destin;
    }
}
