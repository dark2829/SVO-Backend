package com.svo.svo.model;

public class TfavoritosBuilder {
    public static TfavoritosDTO fromVO(TfavoritosVO tfavoritosVO){
        TfavoritosVO origen = tfavoritosVO;
        TfavoritosDTO destin = new TfavoritosDTO();

        destin.setId(origen.getId());
        destin.setIdProducto(origen.getIdProducto());
        destin.setIdUsuario(origen.getIdUsuario());
        return destin;
    }

    public static TfavoritosVO fromDTO(TfavoritosDTO tfavoritosDTO){
        TfavoritosDTO origen = tfavoritosDTO;
        TfavoritosVO destin = new TfavoritosVO();

        destin.setId(origen.getId());
        destin.setIdProducto(origen.getIdProducto());
        destin.setIdUsuario(origen.getIdUsuario());
        return destin;
    }
}
