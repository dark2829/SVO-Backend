package com.svo.svo.service.impl;

import com.sun.tools.jconsole.JConsoleContext;
import com.svo.svo.model.*;
import com.svo.svo.repository.TdireccionRepository;
import com.svo.svo.repository.TpersonaRepository;
import com.svo.svo.repository.TrolRepository;
import com.svo.svo.repository.TusuariosRepository;
import com.svo.svo.service.TpersonaService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class TpersonaServiceImpl implements TpersonaService {

    private static final Logger LOG = LoggerFactory.getLogger(TpersonaServiceImpl.class);

    @Autowired
    private TpersonaRepository tpersonaRepository;

    @Autowired
    private TusuariosRepository tusuariosRepository;

    @Autowired
    private TrolRepository trolRepository;

    @Autowired
    private TdireccionRepository tdireccionRepository;

    @Override
    @Transactional
    public void insertNewUser(String JSON) {
        LOG.info("InsertNewUser-->JSON"+JSON);
        TpersonaVO per = new TpersonaVO();
        TusuariosVO user = new TusuariosVO();
        try{
            JSONObject personObject = new JSONObject(JSON);
            JSONObject userObject = new JSONObject();
            userObject.put("correo",personObject.remove("correo")).toString();
            userObject.put("contrasena",personObject.remove("contrasena")).toString();
            per.setId(0L);
            per.setNombre(personObject.getString("nombre"));
            per.setApellido_paterno(personObject.getString("apellido_paterno"));
            per.setCorreo(userObject.getString("correo"));
            tpersonaRepository.save(per);
            per.setId(tpersonaRepository.findIdByCorreo(per.getCorreo()).getId());
            user.setId(0L);
            user.setCorreo(userObject.getString("correo"));
            user.setContraseña(userObject.getString("contrasena"));
            user.setIdPersona(per);
            user.setIdRol(trolRepository.findById(personObject.getInt("idRol")));
            tusuariosRepository.save(user);
            user.setId(tusuariosRepository.findIdByCorreo(user.getCorreo()).getId());

        }catch (Exception e){
            LOG.error("Error al registrar cliente",e);
        }
    }

    @Override
    public void updateUser(Long idPerson, Long idUser, Map<String, String> data) throws Exception {
        LOG.info("update()->id: {} data: {}", idPerson, data);
        TpersonaVO per = null;
        TusuariosVO user = null;
        TempleadosVO emp = null;
        TtarjetasVO tarjeta = null;
        TdireccionVO direccion =null;
        try{
            Optional<TusuariosVO> userVO = tusuariosRepository.findById(idUser);
            Optional<TrolVO> rol= trolRepository.findById(userVO.get().getIdRol().getId());
            LOG.info("Rol:"+rol);
            if (!userVO.isPresent()) {
                throw new Exception("No se encuentra la persona");
            }
            if(data.containsKey("nombre")){
                userVO.get().getIdPersona().setNombre(data.get("nombre"));
            }
            if(data.containsKey("apellido_paterno")){
                userVO.get().getIdPersona().setApellido_paterno(data.get("apellido_paterno"));
            }
            if(data.containsKey("apellido_materno")){
                userVO.get().getIdPersona().setApellido_materno(data.get("apellido_materno"));
            }
            if(data.containsKey("fecha_nacimiento")){
                Date fechaN = new SimpleDateFormat("dd/MM/yyyy").parse(data.get("fecha_nacimiento"));
                userVO.get().getIdPersona().setFecha_nac(fechaN);
            }
            if(data.containsKey("genero")){
                userVO.get().getIdPersona().setGenero(data.get("genero"));
            }
            if(data.containsKey("correo")){
                userVO.get().getIdPersona().setCorreo(data.get("correo"));
                userVO.get().setCorreo(data.get("correo"));
            }
            if(data.containsKey("contrasena")){
                userVO.get().setContraseña(data.get("contrasena"));
            }
            if(data.containsKey("telefono")){
                userVO.get().getIdPersona().setTelefono(data.get("telefono"));

            }
            if(rol.get().getId() == 3){
                //Direccion
                if(data.containsKey("idDireccion")) {
                    direccion = tdireccionRepository.findDireccionById(Long.valueOf(data.get("idDireccion")));
                }else{
                    direccion = new TdireccionVO();
                }
                if(data.containsKey("calle")){
                    direccion.setCalle(data.get("calle"));
                }
                if(data.containsKey("colonia")){
                    direccion.setColonia(data.get("colonia"));
                }
                if(data.containsKey("municipio")){
                    direccion.setMunicipio(data.get("municipio"));
                }
                if(data.containsKey("estado")){
                    direccion.setEstado(data.get("estado"));
                }
                if(data.containsKey("cp")){
                    direccion.setCp(Integer.parseInt(data.get("cp")));
                }
                if(data.containsKey("n_interior")){
                    direccion.setN_interior(Integer.parseInt(data.get("n_interior")));
                }
                if(data.containsKey("n_exterior")){
                    direccion.setN_exterior(Integer.parseInt(data.get("n_exterior")));
                }
                if(data.containsKey("referencia")){
                    direccion.setReferencia(data.get("referencia"));
                }
                if(!data.containsKey("idDireccion")){
                    tdireccionRepository.save(direccion);
                    userVO.get().getIdPersona().getDireccion().add(direccion);
                }else{
                    tdireccionRepository.save(direccion);
                }

                tpersonaRepository.save(userVO.get().getIdPersona());
                tpersonaRepository.flush();
                tusuariosRepository.save(userVO.get());

//                //tarjetas
//                if(data.containsKey("nombre_propietario")){
//
//                }
//                if(data.containsKey("numero_tarjeta")){
//
//                }
//                if(data.containsKey("fecha_vencimiento")){
//
//                }
//                if(data.containsKey("cvv")){
//
//                }
            }
            if(rol.get().getId() == 2){
                if(data.containsKey("curp")){

                }
                if(data.containsKey("puesto")){

                }
                if(data.containsKey("salario")){

                }
            }

        }catch (Exception e){
            LOG.error("Error al actualizar usuario",e);
        }

    }
}
