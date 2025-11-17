package com.ecosystem.maestros.maestros_service.department.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecosystem.maestros.maestros_service.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    
    Optional<Department> findByCode(String code);
    
    List<Department> findByIsActiveTrue();
    
    boolean existsByCode(String code);
    
    List<Department> findByNameContainingIgnoreCase(String name);
}
