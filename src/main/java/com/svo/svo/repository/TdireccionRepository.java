package com.svo.svo.repository;

import com.svo.svo.model.TdireccionVO;
import com.svo.svo.model.TpersonaVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TdireccionRepository extends JpaRepository<TdireccionVO,Long> {
    TdireccionVO findDireccionById(Long id);
}
