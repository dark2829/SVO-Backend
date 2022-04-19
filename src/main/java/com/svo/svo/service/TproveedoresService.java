package com.svo.svo.service;

import com.svo.svo.model.TproveedoresDTO;

import java.util.List;
import java.util.Map;

public interface TproveedoresService {

    List<TproveedoresDTO> findAllProveedores() throws Exception;
    void insert (TproveedoresDTO tproveedoresDTO) throws Exception;
    void update (Long id, Map<String,String> data) throws Exception;
}
