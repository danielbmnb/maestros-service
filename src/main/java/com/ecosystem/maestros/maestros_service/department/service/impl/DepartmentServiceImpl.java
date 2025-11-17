package com.ecosystem.maestros.maestros_service.department.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecosystem.maestros.maestros_service.common.exception.BadRequestException;
import com.ecosystem.maestros.maestros_service.common.exception.ResourceNotFoundException;
import com.ecosystem.maestros.maestros_service.department.dto.DepartmentRequest;
import com.ecosystem.maestros.maestros_service.department.dto.DepartmentResponse;
import com.ecosystem.maestros.maestros_service.department.entity.Department;
import com.ecosystem.maestros.maestros_service.department.repository.DepartmentRepository;
import com.ecosystem.maestros.maestros_service.department.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService{
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public DepartmentResponse create(DepartmentRequest request) {
        log.info("Creating department with code: {}", request.getCode());
        
        // Validar que el código no exista
        if (departmentRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Ya existe un departamento con el código: " + request.getCode());
        }
        
        // Crear entidad
        Department department = new Department();
        department.setCode(request.getCode());
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setManagerId(request.getManagerId());
        department.setCostCenter(request.getCostCenter());
        department.setIsActive(request.getIsActive());
        
        // Guardar
        Department savedDepartment = departmentRepository.save(department);
        
        log.info("Department created successfully with ID: {}", savedDepartment.getId());
        
        return mapToResponse(savedDepartment);
    }
    
    @Override
    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        log.info("Updating department with ID: {}", id);
        
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
        
        // Validar código único (si cambió)
        if (!department.getCode().equals(request.getCode()) && 
            departmentRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Ya existe un departamento con el código: " + request.getCode());
        }
        
        // Actualizar campos
        department.setCode(request.getCode());
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setManagerId(request.getManagerId());
        department.setCostCenter(request.getCostCenter());
        department.setIsActive(request.getIsActive());
        
        Department updatedDepartment = departmentRepository.save(department);
        
        log.info("Department updated successfully: {}", id);
        
        return mapToResponse(updatedDepartment);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) {
        log.info("Finding department by ID: {}", id);
        
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
        
        return mapToResponse(department);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll() {
        log.info("Finding all departments");
        
        return departmentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAllActive() {
        log.info("Finding all active departments");
        
        return departmentRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting department with ID: {}", id);
        
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
        
        departmentRepository.delete(department);
        
        log.info("Department deleted successfully: {}", id);
    }
    
    @Override
    @Transactional
    public void activate(Long id) {
        log.info("Activating department with ID: {}", id);
        
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
        
        department.setIsActive(true);
        departmentRepository.save(department);
        
        log.info("Department activated successfully: {}", id);
    }
    
    @Override
    @Transactional
    public void deactivate(Long id) {
        log.info("Deactivating department with ID: {}", id);
        
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
        
        department.setIsActive(false);
        departmentRepository.save(department);
        
        log.info("Department deactivated successfully: {}", id);
    }
    
    // Método privado para mapear Entity a Response
    private DepartmentResponse mapToResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .code(department.getCode())
                .name(department.getName())
                .description(department.getDescription())
                .managerId(department.getManagerId())
                .costCenter(department.getCostCenter())
                .isActive(department.getIsActive())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }

}
