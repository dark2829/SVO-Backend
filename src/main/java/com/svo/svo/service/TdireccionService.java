package com.svo.svo.service;

import com.svo.svo.model.TdireccionDTO;
import com.svo.svo.model.TdireccionVO;
import com.svo.svo.other.Utils.AppException;

import java.util.List;

public interface TdireccionService {
    void deleteDireccion(Long idDireccion, Long idPersona) throws AppException;
}
