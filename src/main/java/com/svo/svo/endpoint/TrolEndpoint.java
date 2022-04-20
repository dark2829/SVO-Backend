package com.svo.svo.endpoint;

import com.svo.svo.service.TrolService;
import com.svo.svo.service.TusuariosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rol")
public class TrolEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TrolEndpoint.class);

    @Autowired
    private TrolService trolService;


}
