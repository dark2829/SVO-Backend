package com.svo.svo.repository;

import com.svo.svo.model.TempleadosVO;
import com.svo.svo.model.TpersonaVO;
import com.svo.svo.model.TproductosVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempleadosRepository extends JpaRepository<TempleadosVO, Long> {
    TempleadosVO findIdByCurp(String curp);

    TempleadosVO findEmpleadoById(Integer id);

    List<TempleadosVO> findAllEmpleados();
}
