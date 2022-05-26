package com.svo.svo.service;

import com.svo.svo.other.Utils.AppException;

public interface TtarjetasService {

    void deleteTarjeta(Long idTarjeta, Long idPersona) throws AppException;

}
