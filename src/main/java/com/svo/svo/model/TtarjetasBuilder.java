package com.svo.svo.model;

public class TtarjetasBuilder {
    public TtarjetasBuilder() {
    }

    public static TtarjetasDTO fromVO(TtarjetasVO ttarjetasVO){
        TtarjetasVO origen = ttarjetasVO;
        TtarjetasDTO destin = new TtarjetasDTO();

        destin.setId(origen.getId());
        destin.setNombre_propietario(origen.getNombre_propietario());
        destin.setNumero(origen.getNumero());
        destin.setCvv(origen.getCvv());
        destin.setFecha_vencimiento(destin.getFecha_vencimiento());
        return destin;
    }

    public static TtarjetasVO fromDTO(TtarjetasDTO ttarjetasDTO){
        TtarjetasDTO origen = ttarjetasDTO;
        TtarjetasVO destin = new TtarjetasVO();

        destin.setId(origen.getId());
        destin.setNombre_propietario(origen.getNombre_propietario());
        destin.setNumero(destin.getNumero());
        destin.setCvv(origen.getCvv());
        destin.setFecha_vencimiento(destin.getFecha_vencimiento());
        return destin;
    }
}
