package com.svo.svo.repository;

import com.svo.svo.model.TrolVO;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface TrolRepository extends JpaRepository<TrolVO, Integer> {
    TrolVO findById(int id);

    public Optional<TrolVO> findByName(String name);
}
