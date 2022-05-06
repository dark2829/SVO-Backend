package com.svo.svo.endpoint;

import com.svo.svo.model.TpuestoDTO;
import com.svo.svo.service.TpuestoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/puesto")

public class TpuestosEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(TpuestosEndpoint.class);

    @Autowired
    private TpuestoService puestoService;

    @GetMapping("/buscarPuestos")
    public List<TpuestoDTO> buscarPuestos() {
        List<TpuestoDTO> listPuestos = null;
        LOG.info("buscarPuestos()");
        try {
            listPuestos = puestoService.buscarPuesto();

        } catch (Exception e) {
            LOG.error(String.valueOf(e));
        }
        return listPuestos;
    }

}
