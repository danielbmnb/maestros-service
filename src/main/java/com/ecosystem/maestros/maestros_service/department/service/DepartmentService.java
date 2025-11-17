package com.ecosystem.maestros.maestros_service.department.service;

import java.util.List;

import com.ecosystem.maestros.maestros_service.department.dto.DepartmentRequest;
import com.ecosystem.maestros.maestros_service.department.dto.DepartmentResponse;

public interface DepartmentService {
    
    DepartmentResponse create(DepartmentRequest request);
    
    DepartmentResponse update(Long id, DepartmentRequest request);
    
    DepartmentResponse findById(Long id);
    
    List<DepartmentResponse> findAll();
    
    List<DepartmentResponse> findAllActive();
    
    void delete(Long id);
    
    void activate(Long id);
    
    void deactivate(Long id);
}
