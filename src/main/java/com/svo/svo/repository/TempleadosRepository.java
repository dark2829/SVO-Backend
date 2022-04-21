package com.svo.svo.repository;

import com.svo.svo.model.TempleadosVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempleadosRepository extends JpaRepository<TempleadosVO, Long> {
    TempleadosVO findEmpleadoById(Long id);
}
