package com.svo.svo.service;

import com.svo.svo.model.TpersonaDTO;

import java.text.ParseException;

public interface TpersonaService {
    void insertPersonUserRol(String json) throws Exception, ParseException;
}
