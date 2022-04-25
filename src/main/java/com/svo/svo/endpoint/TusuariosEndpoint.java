package com.svo.svo.endpoint;

import com.svo.svo.model.*;
import com.svo.svo.service.TusuariosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class TusuariosEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TusuariosEndpoint.class);

    @Autowired
    private TusuariosService tusuariosService;

    @GetMapping("/login")
    public TusuariosDTO login (@RequestParam Map<String,String> data){
        TusuariosDTO userDto= null;
        TusuariosVO userVO = null;
        try {
            userVO = tusuariosService.login(data);
            if(userVO!=null){
                userDto = TusuariosBuilder.fromVO(userVO);
            }else {
                LOG.info("No se encontró ninguno usuario");
            }
        }catch(Exception e) {
            LOG.error("No se pudo loggear", e);
        }
        LOG.info(String.valueOf("USUARIO ENCONTRADO: "+userDto));
        return  userDto;
    }

}
