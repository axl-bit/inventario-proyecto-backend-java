package com.example.IM.Empleado;

import com.example.IM.Area.Area;
import com.example.IM.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(name = "doc_identidad", length = 20, unique = true)
    private String docIdentidad;

    @Column(name = "fecha_alta", nullable = false)
    @Builder.Default
    private LocalDateTime fechaAlta = LocalDateTime.now();

    // Relación con Area (muchos empleados pertenecen a un área)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    // Relación opcional con User (1:1). Si es null, el empleado no tiene acceso al sistema.
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true)
    @JsonIgnoreProperties({"empleado", "hibernateLazyInitializer", "handler"})
    private User user;
}