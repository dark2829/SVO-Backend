package com.svo.svo.service;

import com.svo.svo.model.TproductosDTO;
import com.svo.svo.model.TproductosVO;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TproductosService {
    void insert (TproductosDTO tproductosDTO) throws Exception;
    void update (Long id, Map<String,String> data) throws Exception;
    List<TproductosDTO> findAllProductos() throws Exception;
    ResponseEntity<ResponseBody<TproductosVO>> findProductById(Long id) throws AppException;

}
