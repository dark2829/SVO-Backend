package com.svo.svo.repository;

import com.svo.svo.model.TcarritoVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TcarritoRepository extends JpaRepository<TcarritoVO, Long> {
}
