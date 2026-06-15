package com.example.IM.HistoricoAsignacion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
public class HistoricoAsignacionController {

    private final HistoricoAsignacionService historicoService;

    // Historial completo de un equipo
    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<HistoricoAsignacion>> historialPorEquipo(@PathVariable Long equipoId) {
        return ResponseEntity.ok(historicoService.findHistorialByEquipo(equipoId));
    }

    // Historial completo de un empleado
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<HistoricoAsignacion>> historialPorEmpleado(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(historicoService.findHistorialByEmpleado(empleadoId));
    }

    // Equipos actualmente asignados a un empleado (sin devolver)
    @GetMapping("/empleado/{empleadoId}/activos")
    public ResponseEntity<List<HistoricoAsignacion>> equiposActivosPorEmpleado(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(historicoService.findEquiposActivosByEmpleado(empleadoId));
    }
}