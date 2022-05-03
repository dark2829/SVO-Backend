package com.svo.svo.endpoint;

import com.svo.svo.Seguridad.JWTAuthResonseDTO;
import com.svo.svo.Seguridad.JwtTokenProvider;
import com.svo.svo.model.LoginDTO;
import com.svo.svo.model.TusuariosBuilder;
import com.svo.svo.model.TusuariosDTO;
import com.svo.svo.model.TusuariosVO;
import com.svo.svo.other.Utils.AppException;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TusuariosRepository;
import com.svo.svo.service.TpersonaService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TpersonaService tpersonaService;

    @Autowired
    private TusuariosRepository tusuariosRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ResponseBody<LoginDTO>> authenticateUser(@RequestBody LoginDTO loginDTO){
        return Utils.response200OK("Ha iniciado con exito", autentication(loginDTO.getIdentificador(), loginDTO.getContrasena()));
    }

    @PostMapping("/registro")
    public ResponseEntity<ResponseBody<LoginDTO>> registro(@RequestBody String Json) throws AppException {
        LOG.info("<<<<<insertClient() -> JSON: {}", Json);
        ResponseEntity<ResponseBody<LoginDTO>>res=null;
        JSONObject JsonObject = new JSONObject(Json);
        TusuariosVO userVO= null;
        LoginDTO user = null;
        try {
            userVO = tpersonaService.insertNewUser(Json);
            if(userVO!=null){
                user = autentication(userVO.getCorreo(),JsonObject.getString("contrasena"));
                LOG.info(String.valueOf(user));
                res = Utils.response200OK("Bienvenid@ "+userVO.getIdPersona().getNombre(),user);
            }

        } catch (Exception e) {
            res = Utils.response(HttpStatus.BAD_REQUEST,e.getMessage(),null);
        }
        return  res;
    }
    
    public LoginDTO autentication(String identificador, String contrasena){
        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(identificador,contrasena));
        LoginDTO user = new LoginDTO();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TusuariosVO userE = tusuariosRepository.findCorreo(authentication.getName());
        String token = tokenProvider.generarToken(authentication);
        JWTAuthResonseDTO tokenDTO = new JWTAuthResonseDTO(token);
        user.setIdentificador(identificador);
        user.setContrasena(contrasena);
        user.setIdPerson(userE.getIdPersona());
        user.setIdUser(userE);
        user.setTokenAccess(tokenDTO.getTokenDeAcceso());
        user.setRol(authentication.getAuthorities());
        return user;
    }
}
