package com.svo.svo.service.impl;

import com.svo.svo.model.TempleadosVO;
import com.svo.svo.model.TpersonaVO;
import com.svo.svo.model.TusuariosVO;
import com.svo.svo.repository.*;
import com.svo.svo.other.GenerateNempleado;
import com.svo.svo.service.TempleadosService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TempleadosServiceImpl implements TempleadosService {

    private static final Logger LOG = LoggerFactory.getLogger(TpersonaServiceImpl.class);

    @Autowired
    private TempleadosRepository templeadosRepository;

    @Autowired
    private TpersonaRepository tpersonaRepository;

    @Autowired
    private TusuariosRepository tusuariosRepository;

    @Autowired
    private TrolRepository trolRepository;

    @Autowired
    private TpuestoRepository tpuestoRepository;


    @Override
    @Transactional
    public void insertEmpleado(String JSON) throws Exception, ParseException {
        LOG.info("insertEmpleado-->JSON"+JSON);
        TpersonaVO per = new TpersonaVO();
        TusuariosVO user = new TusuariosVO();
        TempleadosVO emp = new TempleadosVO();
        GenerateNempleado num_emp = new GenerateNempleado();
        try{
            JSONObject personObject = new JSONObject(JSON);
            JSONObject empObject = new JSONObject();
            empObject.put("curp",personObject.remove("curp"));
            empObject.put("idPuesto",personObject.remove("idPuesto"));
            empObject.put("salario",personObject.remove("salario"));
            JSONObject userObject = new JSONObject();
            userObject.put("correo",personObject.remove("correo")).toString();
            userObject.put("contrasena",personObject.remove("contrasena")).toString();
            per.setId(0L);
            per.setNombre(personObject.getString("nombre"));
            per.setApellido_paterno(personObject.getString("apellido_paterno"));
            per.setApellido_materno(personObject.getString("apellido_materno"));
            Date fechaN = new SimpleDateFormat("dd/MM/yyyy").parse(personObject.getString("fecha_nacimiento"));
            per.setFecha_nac(fechaN);
            per.setGenero(personObject.getString("genero"));
            per.setTelefono(personObject.getString("telefono"));
            per.setCorreo(userObject.getString("correo"));
            tpersonaRepository.save(per);
            per.setId(tpersonaRepository.findIdByCorreo(per.getCorreo()).getId());
            emp.setId(0);
            emp.setCurp(empObject.getString("curp"));
            emp.setIdPuesto(tpuestoRepository.findPuestoById(empObject.getInt("idPuesto")));
            emp.setSalario(empObject.getLong("salario"));
            emp.setEstatus("Activo");
            emp.setIdPersona(per);
            emp.setNo_empleado("0");
            templeadosRepository.save(emp);
            emp.setId(templeadosRepository.findIdByCurp(emp.getCurp()).getId());
            emp.setNo_empleado(num_emp.agregarCeros(String.valueOf(emp.getId())));
            templeadosRepository.save(emp);
            user.setId(0L);
            user.setCorreo(userObject.getString("correo"));
            user.setContraseña(userObject.getString("contrasena"));
            user.setIdPersona(per);
            user.setIdEmpleado(emp);
            user.setIdRol(trolRepository.findById(personObject.getInt("idRol")));
            tusuariosRepository.save(user);
            user.setId(tusuariosRepository.findIdByCorreo(user.getCorreo()).getId());

        }catch (Exception e){
            LOG.error("Error al registrar empleado",e);
        }
    }
}
