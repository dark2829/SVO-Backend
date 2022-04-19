package com.svo.svo.service;

import com.svo.svo.model.TpuestoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TpuestoService {
    List<TpuestoDTO> buscarPuesto() throws Exception ;
}
