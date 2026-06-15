package com.example.IM.Area;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "area")
public class Area {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;
    
    @Column(name = "s_nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "s_sede", length = 100)
    private String sede;
    
    @Column(name = "c_activo", length = 1)
    @Builder.Default
    private Boolean activo = true;
}