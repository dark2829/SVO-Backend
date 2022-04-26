package com.svo.svo.service.impl;

import com.svo.svo.model.TproveedoresDTO;
import com.svo.svo.model.TusuariosVO;
import com.svo.svo.repository.TusuariosRepository;
import com.svo.svo.service.TusuariosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TusuariosServiceImpl implements TusuariosService {

    private static final Logger LOG = LoggerFactory.getLogger(TusuariosServiceImpl.class);
    @Autowired
    private TusuariosRepository tusuariosRepository;

    public void insert(TproveedoresDTO tproveedoresDTO) throws Exception {

    }

    @Override
    public TusuariosVO login(Map<String, String> data) {
        LOG.info("login DATA --> "+data);
        String ident = null, contrasena = null;
        TusuariosVO user = null;
        /*Datos requeridos
        identificador
        contrasena
        */
        try {
            if (data.containsKey("identificador")) {
                ident = data.get("identificador");
            }
            if ((data.containsKey("contrasena"))) {
                contrasena = data.get("contrasena");
            }
            user = tusuariosRepository.findUserByNoEmp(ident, contrasena);
            if(user==null){
                user = tusuariosRepository.findUserByCorreo(ident,contrasena);
                new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            LOG.error("Error al buscar usuario", e);
        }
        return user;
    }

    @Override
    public TusuariosVO findUserById(Long id) {
        LOG.info("findUserById ()");
        TusuariosVO user= null;
        try{
            user = tusuariosRepository.findUserById(id);
            LOG.info("Usuario encontrado: "+user);
            if (user == null){
                LOG.error("Usuario no encontrado");
            }
        }catch (Exception e){
            LOG.error("NO se pudo buscar usuario",e);
        }

        return user;
    }
}