package com.svo.svo.service;

import com.svo.svo.other.Utils.AppException;

public interface TdireccionService {


    void deleteDireccion(Long idDireccion, Long idPersona) throws AppException;
}
