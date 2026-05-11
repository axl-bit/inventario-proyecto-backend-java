package com.example.IM.Estado;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estado")
public class Estado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;
    
    @Column(name = "s_nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "c_activo", length = 1)
    @Builder.Default
    private Character activo = 'S';
}