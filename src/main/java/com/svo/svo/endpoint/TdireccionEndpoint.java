package com.svo.svo.endpoint;

import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TdireccionService;
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
@RequestMapping("/direcciones")
public class TdireccionEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TdireccionEndpoint.class);

    @Autowired
    private TdireccionService tdireccionService;

    /*
    idDireccion:number
    idPersona:number
     */
    @GetMapping("/deleteDireccion")
    public ResponseEntity<ResponseBody<Void>> deleteDireccion(@RequestParam("idDireccion") Long idDireccion, @RequestParam("idPersona") Long idPersona) {
        ResponseEntity<ResponseBody<Void>> res = null;
        LOG.info("deleteDireccion()->idDireccion: {}", idDireccion);
        try {
            if (idDireccion != null) {
                tdireccionService.deleteDireccion(idDireccion, idPersona);
                res = Utils.response200OK("La direccion fue eliminada exitosamente");
            } else {
                res = Utils.response(HttpStatus.ACCEPTED, "No se seleccionado ninguna direccion", null);
            }

        } catch (Exception e) {
            res = Utils.handle(e, "La direccion no se pudo eliminar");
        }
        return res;
    }


}
