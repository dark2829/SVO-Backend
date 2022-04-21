package com.svo.svo.repository;

import com.svo.svo.model.TpuestoVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TpuestoRepository extends JpaRepository<TpuestoVO, Integer> {
    List<TpuestoVO> buscarPuesto();
    TpuestoVO findPuestoById(Integer id);
}
