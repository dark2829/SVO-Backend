package com.svo.svo.repository;

import com.svo.svo.model.TtarjetasVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TtarjetaRepository extends JpaRepository<TtarjetasVO, Long> {
    TtarjetasVO findTarjetaById(Long id);
}
