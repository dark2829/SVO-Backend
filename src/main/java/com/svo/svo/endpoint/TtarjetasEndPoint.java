package com.svo.svo.endpoint;

import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TtarjetasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tarjetas")
public class TtarjetasEndPoint {

    private static final Logger LOG = LoggerFactory.getLogger(TtarjetasEndPoint.class);

    @Autowired
    private TtarjetasService ttarjetasService;

    /*
  idTarjeta:number
  idPersona:number
   */
    @GetMapping("/deleteTarjeta")
    public ResponseEntity<ResponseBody<Void>> deleteTarjeta(@RequestParam("idTarjeta") Long idTarjeta, @RequestParam("idPersona") Long idPersona) {
        ResponseEntity<ResponseBody<Void>> res = null;
        LOG.info("deleteTarjeta()->idTarjeta: {}", idTarjeta);
        try {
            if(idTarjeta!=null){
                ttarjetasService.deleteTarjeta(idTarjeta, idPersona);
                res = Utils.response200OK("La tarjeta fue eliminada exitosamente");
            }else{
                res = Utils.response(HttpStatus.ACCEPTED, "No se seleccionado ninguna tarjeta", null);
            }

        } catch (Exception e) {
            res = Utils.handle(e, "La tarjeta no se pudo eliminar");
        }
        return res;
    }
}
