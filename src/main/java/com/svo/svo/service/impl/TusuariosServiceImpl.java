package com.svo.svo.service.impl;

import com.svo.svo.model.*;
import com.svo.svo.other.Regex;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.AppException404NotFound;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TusuariosRepository;
import com.svo.svo.service.TusuariosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TusuariosServiceImpl implements TusuariosService {

    private static final Logger LOG = LoggerFactory.getLogger(TusuariosServiceImpl.class);

    @Autowired
    private TusuariosRepository tusuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void insert(TproveedoresDTO tproveedoresDTO) throws Exception {

    }

    @Override
    public ResponseEntity<ResponseBody<TusuariosVO>> login(Map<String, String> data) throws AppException {
        LOG.info("login DATA --> " + data);
        TusuariosVO user = null, findUser = null;
        Regex valid = new Regex();
        String identificador, contrasena = null;
        ;
        boolean isValid;
        ResponseEntity<ResponseBody<TusuariosVO>> res = null;
        try {
            if (data.containsKey("identificador")) {
                identificador = data.get("identificador");
                isValid = valid.validarEmail(identificador);
                if (isValid) { //Verifica que es un correo
                    findUser = tusuariosRepository.findCorreo(identificador);
                    if (findUser == null) {
                        throw new RuntimeException("Correo electrónico no registrado, verifícalo o regístrate");
                    }
                } else {
                    isValid = valid.validarNoEmpleado(identificador);
                    if (isValid) { //verifica que sean 4 digitos
                        findUser = tusuariosRepository.findNoEmpleado(identificador);
                        if (findUser == null) {
                            throw new RuntimeException("No. de empleado incorrecto");
                        }
                    } else { //No corresponde a ningun identificador valido
                        throw new RuntimeException("El campo no es un correo o un No de empleado valido");
                    }
                }
                if ((data.containsKey("contrasena"))) {
                    contrasena = data.get("contrasena");
                    if (Objects.equals(findUser.getContraseña(), contrasena)) {//verifica que la contraseña sea correcta
                        user = findUser;
                        res = Utils.response200OK("Bienvenido " + findUser.getIdPersona().getNombre(), user);
                    } else {
                        throw new RuntimeException("Contraseña incorrecta, ¿Desea recuperar contraseña?” Presione recuperar contraseña");
                    }
                }
            }
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST, "Contraseña incorrecta", null);
        }
        return res;
    }

    @Override
    public TusuariosVO findUserById(Long id) throws AppException {
        LOG.info("findUserById ()");
        TusuariosVO user = null;
        try {
            user = tusuariosRepository.findUserById(id);
            //LOG.info("Usuario encontrado: " + user);
            if (user == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
        } catch (Exception e) {
            Utils.raise(e, e.getMessage());
        }
        return user;
    }

    @Override
    public List<TproductosDTO> mostrarFavoritosPorUsuario(Long idUsuario) throws AppException {
        TusuariosVO usuario = null;
        List<TproductosVO> productosFavoritosVO = new ArrayList<>();
        TproductosDTO tproductosDTO = null;
        List<TproductosDTO> productosFavoritosDTO = new ArrayList<>();

        try{
            usuario = tusuariosRepository.findUserById(idUsuario);
            productosFavoritosVO=usuario.getProductosFavoritos();
            for (TproductosVO productoVO: productosFavoritosVO){
                tproductosDTO = TproductosBuilder.fromVO(productoVO);
                productosFavoritosDTO.add(tproductosDTO);
            }

        }catch (Exception e){
            Utils.raise(e, "Error al buscar favoritos");
        }
        return productosFavoritosDTO;

    }

    @Override
    public void deleteCuenta(Long idUsuario) throws AppException {
        LOG.info("deleteCuenta -> id: {}",idUsuario);
        try {
            TusuariosVO tusuariosVO = tusuariosRepository.findUserById(idUsuario);
            if (tusuariosVO == null){
                throw new AppException404NotFound("No se encuentra el usuario");
            }
            tusuariosRepository.delete(tusuariosVO);
        } catch (Exception e){
            Utils.raise(e,"Error Al eliminar un usuario");
        }
    }

    @Override
    public void actualizarContraseña(String correo, String contraseña) throws AppException {
        TusuariosVO usuario = tusuariosRepository.findByCorreo(correo);
        usuario.setContraseña(passwordEncoder.encode(contraseña));
        tusuariosRepository.save(usuario);
    }


}