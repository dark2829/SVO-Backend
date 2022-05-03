package com.svo.svo.endpoint;


import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TempleadosService;
import com.svo.svo.service.TusuariosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados")
public class TempleadosEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TempleadosEndpoint.class);

    @Autowired
    private TempleadosService templeadosService;

    @PostMapping("/insertEmpleado")
    public ResponseEntity<ResponseBody<String>> insertEmpleado(@RequestBody String Json) {
        LOG.info("<<<<<insertEmpleado() -> JSON: {}", Json);
        ResponseEntity<ResponseBody<String>> res= null;
        try {
            templeadosService.insertEmpleado(Json);
            res = Utils.response200OK("Empleado agregado correctamente");
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST,"Error al registra empleado",null);
        }
        return  res;
    }


}