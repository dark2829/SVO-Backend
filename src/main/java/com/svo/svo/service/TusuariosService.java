package com.svo.svo.service;

import com.svo.svo.model.TusuariosVO;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TusuariosService {
    ResponseEntity<ResponseBody<TusuariosVO>> login(Map<String, String> data) throws AppException;
    TusuariosVO findUserById(Long idPerson);
}
