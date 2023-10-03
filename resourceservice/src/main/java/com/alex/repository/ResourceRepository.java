package com.alex.repository;

import com.alex.model.Resource;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
