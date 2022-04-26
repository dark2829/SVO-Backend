package com.svo.svo.endpoint;

import com.svo.svo.model.*;
import com.svo.svo.service.TpersonaService;
import com.svo.svo.service.TusuariosService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/personas")
public class TpersonaEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TpersonaEndpoint.class);

    @Autowired
    private TpersonaService tpersonaService;

    @Autowired
    private TusuariosService tusuariosService;

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

    @GetMapping("/findUserById")//Buscar una persona
    public ResponseEntity<TusuariosDTO>  findAllProveedores(@RequestParam("id") int id){
        TusuariosDTO userDTO = null;
        TusuariosVO userVO = null;
        LOG.info("findUserById()--> id: "+ id);
        try{
            userVO = tusuariosService.findUserById(Long.valueOf(id));
            userDTO= TusuariosBuilder.fromVO(userVO);

        }catch (Exception e){
            new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<TusuariosDTO>(userDTO,HttpStatus.ACCEPTED);
    }

}
