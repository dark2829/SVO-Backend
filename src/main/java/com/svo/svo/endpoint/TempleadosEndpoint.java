package com.svo.svo.endpoint;


import com.svo.svo.service.TempleadosService;
import com.svo.svo.service.TusuariosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empleados")
public class TempleadosEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TempleadosEndpoint.class);

    @Autowired
    private TempleadosService templeadosService;

    @PostMapping("/insertEmpleado")
    public void insertEmpleado(@RequestBody String Json) {
        LOG.info("<<<<<insertEmpleado() -> JSON: {}", Json);
        try {
            templeadosService.insertEmpleado(Json);
        } catch (Exception e) {
        }
    }


}