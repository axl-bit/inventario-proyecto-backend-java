package com.example.IM.Equipo;

import com.example.IM.Accesorio.Accesorio;
import com.example.IM.Empleado.Empleado;
import com.example.IM.Estado.Estado;
import com.example.IM.HistoricoAsignacion.HistoricoAsignacion;
import com.example.IM.TipoEquipo.TipoEquipo;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipo")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String marca;

    @Column(length = 50)
    private String modelo;

    @Column(name = "sistema_operativo", length = 50)
    private String sistemaOperativo;

    @Column(length = 100)
    private String procesador;

    @Column(length = 20)
    private String ram;

    @Column(length = 50)
    private String almacenamiento;

    @Column(length = 30)
    private String imei;

    @Column(length = 50)
    private String serie;

    @Column(length = 500)
    private String observaciones;

    @Column(name = "fecha_ingreso", nullable = false)
    @Builder.Default
    private LocalDateTime fechaIngreso = LocalDateTime.now();

    @Column(name = "fecha_baja")
    private LocalDateTime fechaBaja;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    @Builder.Default
    private Boolean renovacion = false;

    @Builder.Default
    private Boolean activo = true;

    // FK: Empleado asignado actualmente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    // FK: Tipo de equipo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_equipo_id", nullable = false)
    private TipoEquipo tipoEquipo;

    // FK: Estado del equipo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    // FK: Accesorio (1:1 por ahora)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accesorio_id", nullable = false)
    private Accesorio accesorio;

    // Historial de asignaciones (1:N)
    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HistoricoAsignacion> historialAsignaciones = new ArrayList<>();
}