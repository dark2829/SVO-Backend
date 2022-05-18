package com.svo.svo.repository;

import com.svo.svo.model.TproductosVO;
import com.svo.svo.model.TproveedoresVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TproductosRepository extends JpaRepository<TproductosVO, Long> {
    List<TproductosVO> findAllProductos();
    List<TproductosVO> findStockBajo();
    TproductosVO findProductoById(Long id);


}
