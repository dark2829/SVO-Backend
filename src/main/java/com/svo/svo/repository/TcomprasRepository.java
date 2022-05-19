package com.svo.svo.repository;

import com.svo.svo.model.TcomprasVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TcomprasRepository extends JpaRepository<TcomprasVO, Long> {

    TcomprasVO findCompra(String codigo);


}
