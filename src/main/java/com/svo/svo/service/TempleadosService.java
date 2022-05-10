package com.svo.svo.service;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.List;

public interface TempleadosService {
    void insertEmpleado(String JSON) throws Exception, ParseException;
    List<TempleadosDTO> findAllEmpleados() throws Exception;
     TusuariosVO findEmpleadoById(Integer id) throws AppException;
}
