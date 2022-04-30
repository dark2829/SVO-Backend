package com.svo.svo.endpoint;

import com.svo.svo.model.*;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.service.TpersonaService;
import com.svo.svo.service.TusuariosService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/personas")
public class TpersonaEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TpersonaEndpoint.class);

    @Autowired
    private TpersonaService tpersonaService;

    @Autowired
    private TusuariosService tusuariosService;

    @PostMapping("/insertNewUser")
    public ResponseEntity<ResponseBody<TusuariosDTO>> insertPersonUser(@RequestBody String Json) throws AppException {
        LOG.info("<<<<<insertClient() -> JSON: {}", Json);
        ResponseEntity<ResponseBody<TusuariosDTO>>res=null;
        TusuariosVO userVO= null;
        try {
            userVO = tpersonaService.insertNewUser(Json);
            if(userVO!=null){
                TusuariosDTO userDTO = TusuariosBuilder.fromVO(userVO);
                res = Utils.response(HttpStatus.ACCEPTED,"Bienvenid@ "+userDTO.getIdPersona().getNombre(),userDTO);
            }

        } catch (Exception e) {
            Utils.raise(e,"Error insert usuario");
            res = Utils.response(HttpStatus.BAD_REQUEST,e.getMessage(),null);
        }
        return  res;
    }

        /*Datos para cliente
    nombre
    apellido_paterno
    apellido_materno
    fecha_nacimiento
    genero
    correo
    contrasena
    telefono

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
    @PostMapping("/updateClientDatosGenerales")
    public ResponseEntity<ResponseBody<Void>> updateDatosGenerales(@RequestParam("id") Long idPerson, @RequestParam("idUser") Long idUser, @RequestBody Map<String, String> data){
        ResponseEntity<ResponseBody<Void>> res= null;
        LOG.info("updateClient()-> id: {} data: {}",idPerson,data);
        try {
            tpersonaService.updateUserDatosGenerales(idPerson, idUser, data);
            res= Utils.response(HttpStatus.ACCEPTED, "Informacion actualizada",null);
        } catch (Exception e) {
           res = Utils.response(HttpStatus.BAD_REQUEST,e.getMessage(),null);
        }
        return  res;
    }

//    idDireccion -(si tiene direccion registrada)-
//    calle
//    colonia
//    municipio
//    estado
//    cp
//    n_interior
//    n_exterior
//    referencia
    @PostMapping("/updateClientDirecciones")
    public ResponseEntity<ResponseBody<Void>> updateDirecciones(@RequestParam("id") Long idPerson, @RequestParam("idUser") Long idUser, @RequestBody Map<String, String> data){
        ResponseEntity<ResponseBody<Void>> res= null;
        LOG.info("updateClient()-> id: {} data: {}",idPerson,data);
        try {
            tpersonaService.updateUserDirecciones(idPerson, idUser, data);
            res= Utils.response(HttpStatus.ACCEPTED, "Informacion de direccion actualizada",null);
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST,e.getMessage(),null);
        }
        return  res;
    }

//    idTarjeta (si tiene tarjeta registrada)
//    nombre_propietario
//    numero_tarjeta
//    fecha_vencimiento
//    cvv
    @PostMapping("/updateClientTarjetas")
    public ResponseEntity<ResponseBody<Void>> updateTarjetas(@RequestParam("id") Long idPerson, @RequestParam("idUser") Long idUser, @RequestBody Map<String, String> data){
        ResponseEntity<ResponseBody<Void>> res= null;
        LOG.info("updateClient()-> id: {} data: {}",idPerson,data);
        try {
            tpersonaService.updateUserTarjetas(idPerson, idUser, data);
            res= Utils.response(HttpStatus.ACCEPTED, "Informacion de tarjeta actualizada",null);
        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST,e.getMessage(),null);
        }
        return  res;
    }

    @GetMapping("/findUserById")//Buscar una persona
    public ResponseEntity<ResponseBody<TusuariosDTO>>  findAllProveedores(@RequestParam("id") int id) throws AppException {
        LOG.info("findUserById()--> id: "+ id);
        TusuariosDTO userDTO = null;
        TusuariosVO userVO = null;
        ResponseEntity<ResponseBody<TusuariosDTO>> res=null;
        try{
            userVO = tusuariosService.findUserById(Long.valueOf(id));
            if(userVO != null){
                userDTO= TusuariosBuilder.fromVO(userVO);
                res= Utils.response(HttpStatus.ACCEPTED,"Usuario existente",userDTO)  ;
            }
        }catch (AppException e){
            Utils.raise(e,"Error al buscar Usuario");
            res = Utils.response(HttpStatus.BAD_REQUEST,e.getMessage(),null);
        }
        return res;
    }

}
