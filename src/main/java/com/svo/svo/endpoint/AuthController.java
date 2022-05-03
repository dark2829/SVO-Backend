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
        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginDTO.getIdentificador(),loginDTO.getContrasena()));
        LoginDTO user = loginDTO;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TusuariosVO userE = tusuariosRepository.findCorreo(authentication.getName());
        String token = tokenProvider.generarToken(authentication);
        JWTAuthResonseDTO tokenDTO = new JWTAuthResonseDTO(token);
        user.setIdPerson(userE.getIdPersona());
        user.setIdUser(userE);
        user.setTokenAccess(tokenDTO.getTokenDeAcceso());
        user.setRol(authentication.getAuthorities());
        LOG.info(String.valueOf(user));

        return Utils.response200OK("Ha iniciado con exito",user);
    }

    @PostMapping("/registro")
    public ResponseEntity<ResponseBody<TusuariosDTO>> registro(@RequestBody String Json) throws AppException {
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
            res = Utils.response(HttpStatus.BAD_REQUEST,e.getMessage(),null);
        }
        return  res;
    }
}
