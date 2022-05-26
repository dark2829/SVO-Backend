package com.svo.svo.repository;

import com.svo.svo.model.TpagosVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TpagoRepository extends JpaRepository<TpagosVO, Long> {
}
