package com.svo.svo.repository;

import com.svo.svo.model.TpersonaVO;
import com.svo.svo.model.TusuariosVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TusuariosRepository extends JpaRepository<TusuariosVO, Long> {
    TusuariosVO findIdByCorreo(String correo);

    TusuariosVO findIdEmpleadoByIdUser(Long id);

    TusuariosVO findUserById(Long id);

    TusuariosVO findCorreo(String correo);

    TusuariosVO findNoEmpleado(String noEmpleado);

    public TusuariosVO findByCorreo(String correo);

    public Optional<TusuariosVO> findByNoEmpleado(String noEmpleado);

    Boolean verificarContraseña(String correo, String contrasena);

    TusuariosVO findUserByIdEmpleado(Integer idEmpleado);

    List<TusuariosVO> findUsuariosByRol(String tipo);

}
