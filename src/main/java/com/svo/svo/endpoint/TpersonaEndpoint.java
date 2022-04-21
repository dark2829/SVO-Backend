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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/personas")
public class TpersonaEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TpersonaEndpoint.class);

    @Autowired
    private TpersonaService tpersonaService;

    @PostMapping("/insertNewUser")
    public void insertPersonUser(@RequestBody String Json) {
        LOG.info("<<<<<insertClient() -> JSON: {}", Json);
        try {
            tpersonaService.insertNewUser(Json);
        } catch (Exception e) {
        }
    }

    @PostMapping("/updateClient")
    public void update(@RequestParam("id") Long idPerson, @RequestParam("idUser") Long idUser, @RequestBody Map<String, String> data){
        LOG.info("uptateClient()-> id: {} data: {}",idPerson,data);
        try {
            tpersonaService.updateUser(idPerson, idUser, data);
        } catch (Exception e) {
            LOG.error("Error e");
        }
    }

}
