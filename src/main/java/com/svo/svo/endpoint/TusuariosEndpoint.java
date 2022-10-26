package com.svo.svo.endpoint;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.AppException404NotFound;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TusuariosService;
import org.json.JSONObject;
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

    @GetMapping("/deleteCuenta")
    public ResponseEntity<ResponseBody<Void>> deleteCuenta(Long idUsuario) throws AppException {
        ResponseEntity<ResponseBody<Void>> res = null;
        LOG.info("deleted()->id: {}", idUsuario);
        try {
            tusuariosService.deleteCuenta(idUsuario);
            res = Utils.response200OK("La cuenta fue eliminada exitosamente");
        } catch (Exception e) {
            res = Utils.handle(e, "La cuenta no se pudo eliminar");
        }
        return res;
    }

    /*
    url: correo:string
    body: contraseña
     */
    @PostMapping("/actualizarContraseña")
    public ResponseEntity<ResponseBody<Void>>  actualizarContraseña(@RequestParam("correo") String correo, @RequestBody String contraseña) throws AppException {
        ResponseEntity<ResponseBody<Void>> res = null;
        LOG.info("actualizarContraseña()->id: {}"+correo);

        JSONObject nuevaContraseña = new JSONObject(contraseña);
        try {
            tusuariosService.actualizarContraseña(correo, nuevaContraseña.getString("contraseña"));
            res = Utils.response200OK("Su contraseña a sido actualizada");
        } catch (Exception e) {
            res = Utils.handle(e, "Error al actualizar contraseña");
        }
        return res;
    }
}
