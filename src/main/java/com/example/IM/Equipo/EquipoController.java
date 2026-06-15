package com.example.IM.Equipo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoService equipoService;

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Equipo>> findAll() {
        return ResponseEntity.ok(equipoService.findAll());
    }

    // Obtener activos
    @GetMapping("/activos")
    public ResponseEntity<List<Equipo>> findActivos() {
        return ResponseEntity.ok(equipoService.findActivos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> findById(@PathVariable Long id) {
        return ResponseEntity.ok(equipoService.findById(id));
    }

    // Buscar por empleado
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<Equipo>> findByEmpleado(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(equipoService.findByEmpleado(empleadoId));
    }

    // Buscar por estado
    @GetMapping("/estado/{estadoId}")
    public ResponseEntity<List<Equipo>> findByEstado(@PathVariable Long estadoId) {
        return ResponseEntity.ok(equipoService.findByEstado(estadoId));
    }

    // Buscar por tipo
    @GetMapping("/tipo/{tipoEquipoId}")
    public ResponseEntity<List<Equipo>> findByTipoEquipo(@PathVariable Long tipoEquipoId) {
        return ResponseEntity.ok(equipoService.findByTipoEquipo(tipoEquipoId));
    }

    // Búsqueda con filtros
    @GetMapping("/buscar")
    public ResponseEntity<List<Equipo>> buscarConFiltros(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Long empleadoId,
            @RequestParam(required = false) Long estadoId,
            @RequestParam(required = false) Long tipoEquipoId) {
        return ResponseEntity.ok(equipoService.buscarConFiltros(
                marca, modelo, empleadoId, estadoId, tipoEquipoId));
    }

    // Crear equipo
    @PostMapping
    public ResponseEntity<Equipo> create(@RequestBody Equipo equipo) {
        return ResponseEntity.ok(equipoService.save(equipo));
    }

    // Actualizar equipo
    @PutMapping("/{id}")
    public ResponseEntity<Equipo> update(@PathVariable Long id, @RequestBody Equipo equipo) {
        return ResponseEntity.ok(equipoService.update(id, equipo));
    }

    // Dar de baja
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Asignar a empleado
    @PutMapping("/{equipoId}/asignar/{empleadoId}")
    public ResponseEntity<Equipo> asignarEmpleado(
            @PathVariable Long equipoId,
            @PathVariable Long empleadoId,
            @RequestBody(required = false) Map<String, String> body) {
        String observaciones = body != null ? body.getOrDefault("observaciones", "") : "";
        return ResponseEntity.ok(equipoService.asignarEmpleado(equipoId, empleadoId, observaciones));
    }

    // Devolver equipo
    @PutMapping("/{equipoId}/devolver")
    public ResponseEntity<Equipo> devolverEquipo(
            @PathVariable Long equipoId,
            @RequestBody(required = false) Map<String, String> body) {
        String observaciones = body != null ? body.getOrDefault("observaciones", "") : "";
        return ResponseEntity.ok(equipoService.devolverEquipo(equipoId, observaciones));
    }
}