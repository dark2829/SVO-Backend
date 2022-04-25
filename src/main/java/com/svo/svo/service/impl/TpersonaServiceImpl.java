package com.svo.svo.service.impl;


import com.svo.svo.model.*;
import com.svo.svo.repository.*;
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

    @Autowired
    private TtarjetaRepository ttarjetasRepository;

    @Autowired
    private TempleadosRepository templeadosRepository;

    @Autowired
    private TpuestoRepository tpuestoRepository;

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
    @Transactional
    public void updateUser(Long idPerson, Long idUser, Map<String, String> data) throws Exception {
        LOG.info("update()->id: {} data: {}", idPerson, data);
        TpersonaVO per = null;
        TusuariosVO user = null;
        TempleadosVO emp = null;
        TtarjetasVO tarjeta = null;
        TdireccionVO direccion =null;
        try{
        /*Datos para cliente
        nombre
        apellido_paterno
        apellido_materno
        fecha_nacimiento
        genero
        correo
        contrasena
        telefono
        idDireccion -(si tiene direccion registrada)-
        calle
        colonia
        municipio
        estado
        cp
        n_interior
        n_exterior
        referencia
        idTarjeta (si tiene tarjeta registrada)
        nombre_propietario
        numero_tarjeta
        fecha_vencimiento
        cvv

        //Datos para empleado
        nombre
        apellido_paterno
        apellido_materno
        fecha_nacimiento
        genero
        correo
        contrasena
        telefono
        curp
        idPuesto
        salario
        estatus
        */
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
            //Usuario cliente
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

                //tarjetas
                if(data.containsKey("idTarjeta")) {
                    tarjeta = ttarjetasRepository.findTarjetaById(Long.valueOf(data.get("idTarjeta")));
                }else{
                    tarjeta = new TtarjetasVO();
                }
                if(data.containsKey("nombre_propietario")){
                    tarjeta.setNombre_propietario(data.get("nombre_propietario"));
                }
                if(data.containsKey("numero_tarjeta")){
                    tarjeta.setNumero(data.get("numero_tarjeta"));
                }
                if(data.containsKey("fecha_vencimiento")){
                    tarjeta.setFecha_vencimiento(data.get("fecha_vencimiento"));
                }
                if(data.containsKey("cvv")){
                    tarjeta.setCvv(Integer.parseInt(data.get("cvv")));
                }
                if(!data.containsKey("idTarjeta")){
                    ttarjetasRepository.save(tarjeta);
                    userVO.get().getIdPersona().getTarjeta().add(tarjeta);
                }else{
                    ttarjetasRepository.save(tarjeta);
                }

            }
            //Usuario empleado
            if(rol.get().getId() == 2){
                emp = tusuariosRepository.findIdEmpleadoByIdUser(userVO.get().getId()).getIdEmpleado();
                if(data.containsKey("curp")){
                    emp.setCurp(data.get("curp"));
                }
                if(data.containsKey("idPuesto")){
                    emp.setIdPuesto(tpuestoRepository.findPuestoById(Integer.valueOf(data.get("idPuesto"))));
                }
                if(data.containsKey("salario")){
                    emp.setSalario(Float.parseFloat(data.get("salario")));
                }
                if(data.containsKey("estatus")){
                    emp.setEstatus(data.get("estatus"));
                }
                templeadosRepository.save(emp);
            }

            tpersonaRepository.save(userVO.get().getIdPersona());
            tpersonaRepository.flush();
            tusuariosRepository.save(userVO.get());

        }catch (Exception e){
            LOG.error("Error al actualizar usuario",e);
        }

    }
}
