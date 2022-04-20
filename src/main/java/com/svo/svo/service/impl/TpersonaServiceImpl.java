package com.svo.svo.service.impl;

import com.svo.svo.model.TpersonaDTO;
import com.svo.svo.service.TpersonaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class TpersonaServiceImpl implements TpersonaService {

    private static final Logger LOG = LoggerFactory.getLogger(TpersonaServiceImpl.class);

    @Override
    public void insertPersonUserRol(String JSON) throws Exception, ParseException {
        LOG.info("InsertPersonRol() JSON-->",JSON);

    }
}
