package com.svo.svo.endpoint;

import com.svo.svo.model.TpersonaDTO;
import com.svo.svo.model.TproveedoresDTO;
import com.svo.svo.service.TpersonaService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personas")
public class TpersonaEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TpersonaEndpoint.class);

    @Autowired
    private TpersonaService tpersonaService;

    @PostMapping("/insertClient")
    public void insertPersonUser(@RequestBody String Json) {
        LOG.info("<<<<<insertClient() -> JSON: {}", Json);
        try {
            JSONObject personObject = new JSONObject(Json);
            tpersonaService.insertPersonUserRol(Json);
        } catch (Exception e) {
        }
    }

}
