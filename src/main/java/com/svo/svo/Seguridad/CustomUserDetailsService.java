package com.svo.svo.Seguridad;

import com.svo.svo.model.TrolVO;
import com.svo.svo.model.TusuariosVO;
import com.svo.svo.repository.TrolRepository;
import com.svo.svo.repository.TusuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TrolRepository trolRepository;

    @Autowired
    private TusuariosRepository tusuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        TusuariosVO usuario = tusuariosRepository.findByCorreo(usernameOrEmail) != null ? tusuariosRepository.findByCorreo(usernameOrEmail) : tusuariosRepository.findByNoEmpleado(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado "+ usernameOrEmail));
        return new User(usuario.getCorreo(),usuario.getContraseña(),mapearRoles(usuario.getIdRol()));
    }

    private Collection<? extends GrantedAuthority> mapearRoles(TrolVO roles){
        return trolRepository.findAll().stream().map(rol -> new SimpleGrantedAuthority(roles.getTipo())).collect(Collectors.toList());
    }
}
