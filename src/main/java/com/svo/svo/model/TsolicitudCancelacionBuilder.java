package com.svo.svo.model;

public class TsolicitudCancelacionBuilder {
    public TsolicitudCancelacionBuilder() {
    }

    public static TsolicitudCancelacionDTO fromVO(TsolicitudCancelacionVO tsolicitudCancelacionVO){
        TsolicitudCancelacionVO origen = tsolicitudCancelacionVO;
        TsolicitudCancelacionDTO destin = new TsolicitudCancelacionDTO();

        destin.setId(origen.getId());
        destin.setEstatus(origen.getEstatus());
        destin.setMotivo_cancel(origen.getMotivo_cancel());
        destin.setMotivo_resp(origen.getMotivo_resp());
        return destin;
    }

    public static TsolicitudCancelacionVO fromDTO(TsolicitudCancelacionDTO tsolicitudCancelacionDTO){
        TsolicitudCancelacionDTO origen = tsolicitudCancelacionDTO;
        TsolicitudCancelacionVO destin = new TsolicitudCancelacionVO();

        destin.setId(origen.getId());
        destin.setEstatus(origen.getEstatus());
        destin.setMotivo_cancel(origen.getMotivo_cancel());
        destin.setMotivo_resp(origen.getMotivo_resp());
        return destin;
    }
}
