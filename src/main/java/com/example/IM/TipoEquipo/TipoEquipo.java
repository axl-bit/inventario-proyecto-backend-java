package com.example.IM.TipoEquipo;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipo_equipo")
public class TipoEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "es_movil")
    @Builder.Default
    private Boolean esMovil = false;

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;
}