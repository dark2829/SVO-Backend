package com.svo.svo.service;

import com.svo.svo.model.TproveedoresDTO;
import com.svo.svo.model.TusuariosVO;

import java.util.Map;

public interface TusuariosService {
    TusuariosVO login(Map<String, String> data);
    TusuariosVO findUserById(Long idPerson);
}
