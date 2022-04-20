package com.svo.svo.endpoint;

import com.svo.svo.model.TproveedoresDTO;
import com.svo.svo.service.TusuariosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class TusuariosEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TusuariosEndpoint.class);

    @Autowired
    private TusuariosService tusuariosService;


}
