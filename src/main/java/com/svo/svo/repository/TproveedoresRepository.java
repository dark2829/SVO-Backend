package com.svo.svo.repository;

import com.svo.svo.model.TproveedoresVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TproveedoresRepository extends JpaRepository<TproveedoresVO, Long> {
    List<TproveedoresVO> findAllProveedores();

    TproveedoresVO findById(int id);
}
