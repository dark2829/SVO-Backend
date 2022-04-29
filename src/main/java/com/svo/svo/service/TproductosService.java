package com.svo.svo.service;

import com.svo.svo.model.TproductosDTO;
import com.svo.svo.model.TproveedoresDTO;

import java.util.List;
import java.util.Map;

public interface TproductosService {
    void insert (TproductosDTO tproductosDTO) throws Exception;
    void update (Long id, Map<String,String> data) throws Exception;
    List<TproductosDTO> findAllProductos() throws Exception;

}
