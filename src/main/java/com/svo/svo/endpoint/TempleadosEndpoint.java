package com.svo.svo.endpoint;


import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TempleadosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class TempleadosEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TempleadosEndpoint.class);

    @Autowired
    private TempleadosService templeadosService;

    @PreAuthorize("hasAuthority('Administrador')")
    @PostMapping("/insertEmpleado")
    public ResponseEntity<ResponseBody<String>> insertEmpleado(@RequestBody String Json) {
        LOG.info("<<<<<insertEmpleado() -> JSON: {}", Json);
        ResponseEntity<ResponseBody<String>> res = null;
        try {
            templeadosService.insertEmpleado(Json);
            res = Utils.response200OK("Empleado agregado correctamente");
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, "Error al registra empleado", null);
        }
        return res;
    }

    @GetMapping("/findEmpleadoById")//Buscar un producto
    public ResponseEntity<ResponseBody<TempleadosDTO>> findEmpleadoById(@RequestParam("id") int id) throws AppException {
        LOG.info("findEmpleadoById()--> id: " + id);
        TempleadosDTO templeadosDTO = null;
        ResponseEntity<ResponseBody<TempleadosVO>> templeadoVO = null;
        ResponseEntity<ResponseBody<TempleadosDTO>> res = null;
        try {
            templeadoVO = templeadosService.findEmpleadoById(id);
            if (templeadoVO.getBody().getData() != null) {
                templeadosDTO = TempleadosBuilder.fromVO(templeadoVO.getBody().getData());
                res = Utils.response(HttpStatus.ACCEPTED, "Empleado existente", templeadosDTO);
            } else {
                res = Utils.response(HttpStatus.BAD_REQUEST, templeadoVO.getBody().getMessage(), null);
            }
        } catch (AppException e) {
            Utils.raise(e, "Error al buscar empleado");
        }
        return res;
    }

    @GetMapping("/findAllEmpleados")
    public ResponseEntity<ResponseBody<List<TempleadosDTO>>> findAllEmpleados() throws AppException {
        List<TempleadosDTO> listEmpleados = null;
        ResponseEntity<ResponseBody<List<TempleadosDTO>>> res = null;
        LOG.info("findAllEmpleados()");
        try {
            listEmpleados = templeadosService.findAllEmpleados();
            res = Utils.response200OK("Lista de empleados", listEmpleados);
        } catch (Exception e) {
            Utils.raise(e, "Error al buscar todos los empleados");
        }
        return res;
    }


}