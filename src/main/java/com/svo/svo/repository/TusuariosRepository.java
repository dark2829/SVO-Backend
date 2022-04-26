package com.svo.svo.repository;

import com.svo.svo.model.TusuariosVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TusuariosRepository extends JpaRepository<TusuariosVO, Long> {
    TusuariosVO findIdByCorreo(String correo);
    TusuariosVO findIdEmpleadoByIdUser(Long id);
    TusuariosVO findUserByNoEmp(String ident, String contrasena);
    TusuariosVO findUserByCorreo(String correo, String contrasena);
    TusuariosVO findUserById(Long id);

}
