package com.ecosystem.maestros.maestros_service.department.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecosystem.maestros.maestros_service.common.dto.ApiResponse;
import com.ecosystem.maestros.maestros_service.department.dto.DepartmentRequest;
import com.ecosystem.maestros.maestros_service.department.dto.DepartmentResponse;
import com.ecosystem.maestros.maestros_service.department.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;

    @PostMapping(value = "/departments-save")
    public ResponseEntity<ApiResponse<DepartmentResponse>> create(
            @Valid @RequestBody DepartmentRequest request) {
        log.info("POST /api/departments - Creating department");
        
        DepartmentResponse response = departmentService.create(request);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Departamento creado exitosamente"));
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {
        log.info("PUT /api/departments/{} - Updating department", id);
        
        DepartmentResponse response = departmentService.update(id, request);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Departamento actualizado exitosamente"));
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> findById(@PathVariable Long id) {
        log.info("GET /api/departments/{} - Finding department by ID", id);
        
        DepartmentResponse response = departmentService.findById(id);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Departamento encontrado"));
    }
    
    @GetMapping(value = "/get-all-departments")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> findAll() {
        log.info("GET /api/departments - Finding all departments");
        
        List<DepartmentResponse> response = departmentService.findAll();
        
        return ResponseEntity.ok(ApiResponse.success(response, "Departamentos obtenidos exitosamente"));
    }
    
    @GetMapping(value = "/active")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> findAllActive() {
        log.info("GET /api/departments/active - Finding active departments");
        
        List<DepartmentResponse> response = departmentService.findAllActive();
        
        return ResponseEntity.ok(ApiResponse.success(response, "Departamentos activos obtenidos exitosamente"));
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("DELETE /api/departments/{} - Deleting department", id);
        
        departmentService.delete(id);
        
        return ResponseEntity.ok(ApiResponse.success(null, "Departamento eliminado exitosamente"));
    }
    
    @PatchMapping(value = "/{id}/activate")
    public ResponseEntity<ApiResponse<Void>> activate(@PathVariable Long id) {
        log.info("PATCH /api/departments/{}/activate - Activating department", id);
        
        departmentService.activate(id);
        
        return ResponseEntity.ok(ApiResponse.success(null, "Departamento activado exitosamente"));
    }
    
    @PatchMapping(value = "/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable Long id) {
        log.info("PATCH /api/departments/{}/deactivate - Deactivating department", id);
        
        departmentService.deactivate(id);
        
        return ResponseEntity.ok(ApiResponse.success(null, "Departamento desactivado exitosamente"));
    }
}
