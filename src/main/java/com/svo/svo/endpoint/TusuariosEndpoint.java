package com.svo.svo.endpoint;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TusuariosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class TusuariosEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TusuariosEndpoint.class);

    @Autowired
    private TusuariosService tusuariosService;

    @GetMapping("/login")
    public ResponseEntity<ResponseBody<TusuariosDTO>> login (@RequestParam Map<String,String> data){
        /*Datos requeridos
        identificador
        contrasena
        */
        TusuariosDTO userDto= null;
        ResponseEntity<ResponseBody<TusuariosVO>> userVO = null;
        ResponseEntity<ResponseBody<TusuariosDTO>> res = null;

        try {
            userVO = tusuariosService.login(data);
            if(userVO.getBody().getData()!=null){
                userDto = TusuariosBuilder.fromVO(userVO.getBody().getData());
                LOG.info(String.valueOf("USUARIO ENCONTRADO: "+userDto));
                res= Utils.response200OK("Usuario registrado",userDto);
            }else {
                res=Utils.response(HttpStatus.BAD_REQUEST,userVO.getBody().getMessage(),null);
            }
        }catch(Exception e) {
           res= Utils.handle(e,"Error en el loggin");
        }
        return  res;
    }

}
