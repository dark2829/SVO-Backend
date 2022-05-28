package com.svo.svo.service;

import com.svo.svo.model.TpersonaDTO;
import com.svo.svo.model.TusuariosVO;
import com.svo.svo.other.Utils.AppException;

import java.text.ParseException;
import java.util.Map;

public interface TpersonaService {
    TusuariosVO insertNewUser(String JSON) throws Exception, ParseException;
    void updateUserDatosGenerales(Long id,Long idUser, Map<String, String> data) throws AppException;
    void updateUserDirecciones(Long idPersona,int index, Map<String, String> data) throws AppException;
    void updateUserTarjetas(Long id,int index, Map<String, String> data) throws AppException;
    byte[] actualizarFotoPerfil(Long idPersona, byte[] img) throws AppException;
}