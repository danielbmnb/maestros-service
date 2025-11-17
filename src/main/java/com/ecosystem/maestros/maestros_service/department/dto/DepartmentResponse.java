package com.ecosystem.maestros.maestros_service.department.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse implements Serializable{
 
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long managerId;
    private String costCenter;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}
