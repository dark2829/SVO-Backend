package com.svo.svo.service;

import com.svo.svo.model.TpersonaDTO;

import java.text.ParseException;
import java.util.Map;

public interface TpersonaService {
    void insertNewUser(String JSON) throws Exception, ParseException;
    void updateUser(Long id,Long idUser, Map<String, String> data) throws Exception;
}
