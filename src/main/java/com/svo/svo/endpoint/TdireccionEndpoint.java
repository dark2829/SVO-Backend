package com.svo.svo.endpoint;

import com.svo.svo.service.TdireccionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direccion")
public class TdireccionEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TdireccionEndpoint.class);

    @Autowired
    private TdireccionService tdireccionService;


}
