package com.svo.svo.repository;

import com.svo.svo.model.TpersonaVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TpersonaRepository extends JpaRepository<TpersonaVO, Long> {
    TpersonaVO findIdByCorreo(String correo);

}
