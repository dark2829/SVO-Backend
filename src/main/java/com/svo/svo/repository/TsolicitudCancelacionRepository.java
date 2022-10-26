package com.svo.svo.repository;

import com.svo.svo.model.TsolicitudCancelacionVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TsolicitudCancelacionRepository extends JpaRepository<TsolicitudCancelacionVO, Long> {
}
