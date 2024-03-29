package com.svo.svo.service.impl;


import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.*;
import com.svo.svo.service.TpersonaService;
import jdk.jshell.execution.Util;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TusuariosVO insertNewUser(String JSON) throws AppException {
        LOG.info("InsertNewUser-->JSON"+JSON);
        TpersonaVO per = new TpersonaVO();
        TusuariosVO user = new TusuariosVO();
        TusuariosVO userExist;
        List<TdireccionVO> direcciones = new ArrayList<>();
        List<TtarjetasVO> tarjetas = new ArrayList<>();
        try{
            JSONObject personObject = new JSONObject(JSON);
            JSONObject userObject = new JSONObject();
            userObject.put("correo",personObject.remove("correo")).toString();
            userObject.put("contrasena",personObject.remove("contrasena")).toString();
            per.setId(0L);
            per.setNombre(personObject.getString("nombre"));
            per.setApellido_paterno(personObject.getString("apellido_paterno"));
            userExist = tusuariosRepository.findCorreo(userObject.getString("correo"));
            if(userExist != null){
                throw new RuntimeException("Correo ya registrado, ingrese un correo distinto");
            }else{
                per.setCorreo(userObject.getString("correo"));
                for(int i=0;i<3;i++){
                    direcciones.add(new TdireccionVO());
                    direcciones.get(i).setCp(-1);
                    direcciones.get(i).setN_exterior(-1);
                    direcciones.get(i).setN_interior(-1);
                    LOG.info(""+direcciones.get(i));
                    tdireccionRepository.save(direcciones.get(i));
                }
                per.setDireccion(direcciones);
               // tpersonaRepository.save(per);
                tpersonaRepository.flush();
                for(int i=0;i<3;i++){
                    tarjetas.add(new TtarjetasVO());
                    ttarjetasRepository.save(tarjetas.get(i));
                }
                per.setTarjeta(tarjetas);
                tpersonaRepository.saveAndFlush(per);
                per.setId(tpersonaRepository.findIdByCorreo(per.getCorreo()).getId());
                user.setId(0L);
                user.setCorreo(userObject.getString("correo"));
                user.setContraseña(passwordEncoder.encode(userObject.getString("contrasena")));
                user.setIdPersona(per);
                user.setIdRol(trolRepository.findById(personObject.getInt("idRol")));
                tusuariosRepository.save(user);
                user.setId(tusuariosRepository.findIdByCorreo(user.getCorreo()).getId());
            }
            LOG.info(String.valueOf(user));
            //actualizarDirecciones();
            //actualizarTarjetas();

        }catch (Exception e){
            Utils.raise(e,e.getMessage());
        }
        return user;
    }

    @Override
    @Transactional
    public void updateUserDatosGenerales(Long idPerson, Long idUser, Map<String, String> data) throws AppException {
        LOG.info("update()->id: {} data: {}", idPerson, data);
        TpersonaVO per = null;
        TusuariosVO user = null;
        TempleadosVO emp = null;
        TusuariosVO userEncontrado = null;
        String correo;
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
            	LOG.info(data.get("fecha_nacimiento"));
                if (data.get("fecha_nacimiento") != null){
                    Date fechaN = new SimpleDateFormat("dd/MM/yyyy").parse(data.get("fecha_nacimiento"));
                    LOG.info(String.valueOf(new SimpleDateFormat("DD/MM/yyyy").parse(data.get("fecha_nacimiento"))));
                    userVO.get().getIdPersona().setFecha_nac(fechaN);
                }
            }
            if(data.containsKey("genero")){
            	LOG.info(data.get("genero"));
            	if(data.get("genero")!= "") {
                userVO.get().getIdPersona().setGenero(data.get("genero"));
            	}
            }
            if(data.containsKey("correo")) {
                correo = data.get("correo");
                userEncontrado = tusuariosRepository.findCorreo(correo);
                if (userEncontrado != null && !Objects.equals(userEncontrado.getId(), idUser)) {
                    throw new RuntimeException("Correo ya registrado");
                } else {
                    userVO.get().getIdPersona().setCorreo(correo);
                    userVO.get().setCorreo(correo);
                }
            }
            if(data.containsKey("contrasena")){
                userVO.get().setContraseña(passwordEncoder.encode(data.get("contrasena")));
            }
            if(data.containsKey("telefono")){
                userVO.get().getIdPersona().setTelefono(data.get("telefono"));

            }
            //Usuario empleado
            if(rol.get().getId() == 2){
                emp = tusuariosRepository.findIdEmpleadoByIdUser(userVO.get().getId()).getIdEmpleado();
                if(data.containsKey("curp")){
                	TempleadosVO empleadoExiste = templeadosRepository.buscarCurpDuplicado(data.get("curp"));
                    if (empleadoExiste != null && !Objects.equals(empleadoExiste.getId(), userVO.get().getIdEmpleado().getId())) {
                        throw new RuntimeException("La CURP ya ha sido registrada");
                    }
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
            Utils.raise(e,e.getMessage());
        }

    }

    @Override
    public void updateUserDirecciones(Long idPersona, int index, Map<String, String> data) throws AppException {
        //Direccion

        TdireccionVO direccion = new TdireccionVO();
        Optional <TpersonaVO> persona = Optional.of(tpersonaRepository.getById(idPersona));
        if (!persona.isPresent()) {
            throw new AppException("No se encuentra la persona");
        }
        try{
            if(data.containsKey("idDireccion")) {
                direccion = tdireccionRepository.findDireccionById(Long.valueOf(data.get("idDireccion")));
            }else{
                throw new RuntimeException("Te falto agregar el idDireccion");
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
                if((data.get("cp"))!="") {
                    direccion.setCp(Integer.parseInt(data.get("cp")));
                }
            }
            if(data.containsKey("n_interior")){
                if(data.get("n_interior") != null) {
                    direccion.setN_interior(Integer.parseInt(data.get("n_interior")));
                }
            }
            if(data.containsKey("n_exterior")){
                if(data.get("n_exterior") != "") {
                    direccion.setN_exterior(Integer.parseInt(data.get("n_exterior")));
                }
            }
            if(data.containsKey("referencia")){
                direccion.setReferencia(data.get("referencia"));
            }
            tdireccionRepository.save(direccion);
            persona.get().getDireccion().set(index,direccion);
            tpersonaRepository.save(persona.get());
            tpersonaRepository.flush();
        }catch (Exception e){
            Utils.raise(e,"Error al editar una direccion");
        }


    }

    //metodo para actualizar usuarios anteriores
    public void actualizarDirecciones() throws AppException {
        List<TusuariosVO> usuarios = tusuariosRepository.findUsuariosByRol("Cliente");
        TpersonaVO persona;
        try{
            for(TusuariosVO usuario: usuarios){
                List<TdireccionVO> direccionesNuevas = new ArrayList<>();
                persona = usuario.getIdPersona();
                for(int i=0;i<3;i++){
                    direccionesNuevas.add(new TdireccionVO());
                    tdireccionRepository.save(direccionesNuevas.get(i));
                }
                persona.setDireccion(direccionesNuevas);
                tpersonaRepository.saveAndFlush(persona);
            }
        }catch (Exception e){
            Utils.raise(e,"Error agregando direccion vacias");
        }
    }

    @Override
    public void updateUserTarjetas(Long idPersona, int index, Map<String, String> data) throws AppException {
        TtarjetasVO tarjeta = new TtarjetasVO();
        Optional <TpersonaVO> persona = Optional.of(tpersonaRepository.getById(idPersona));
        if (!persona.isPresent()) {
            throw new AppException("No se encuentra la persona");
        }
        //tarjetas
        if(data.containsKey("idTarjeta")) {
            tarjeta = ttarjetasRepository.findTarjetaById(Long.valueOf(data.get("idTarjeta")));
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
        ttarjetasRepository.save(tarjeta);
        persona.get().getTarjeta().set(index,tarjeta);
        tpersonaRepository.save(persona.get());
        tpersonaRepository.flush();
    }

    @Override
    public byte[] actualizarFotoPerfil(Long idPersona,TpersonaDTO img) throws AppException {
        Optional <TpersonaVO> persona = Optional.of(tpersonaRepository.getById(idPersona));
        byte[] img1 =null;
        try {
            persona.get().setFoto(img.getFoto());
            tpersonaRepository.save(persona.get());
            img1=persona.get().getFoto();
        }catch (Exception e){
            Utils.raise(e,"Error al inserta foto de perfil");
        }
        return persona.get().getFoto();
    }

    //metodo para actualizar usuarios anteriores
    public void actualizarTarjetas() throws AppException {
        List<TusuariosVO> usuarios = tusuariosRepository.findUsuariosByRol("Cliente");
        TpersonaVO persona;
        try{
            for(TusuariosVO usuario: usuarios){
                List<TtarjetasVO> tarjetasNuevas = new ArrayList<>();
                persona = usuario.getIdPersona();
                for(int i=0;i<3;i++){
                    tarjetasNuevas.add(new TtarjetasVO());
                    ttarjetasRepository.save(tarjetasNuevas.get(i));
                }
                persona.setTarjeta(tarjetasNuevas);
                tpersonaRepository.saveAndFlush(persona);
            }
        }catch (Exception e){
            Utils.raise(e,"Error agregando tarjetas vacias");
        }
    }
}
