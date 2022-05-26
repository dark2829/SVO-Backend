package com.svo.svo.service;

import com.svo.svo.model.TproductosDTO;
import com.svo.svo.model.TusuariosVO;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TusuariosService {
    ResponseEntity<ResponseBody<TusuariosVO>> login(Map<String, String> data) throws AppException;
    TusuariosVO findUserById(Long idPerson) throws AppException;
    List<TproductosDTO> mostrarFavoritosPorUsuario(Long idUsuario) throws AppException;
    void deleteCuenta(Long idUsuario) throws AppException;
    void actualizarContraseña(String correo, String contraseña) throws AppException;

}
