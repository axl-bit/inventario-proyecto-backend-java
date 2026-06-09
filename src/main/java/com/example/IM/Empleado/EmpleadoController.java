package com.example.IM.Empleado;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    // Obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<Empleado>> findAll() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    // Obtener empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> findById(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.findById(id));
    }

    // Buscar empleados por área
    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<Empleado>> findByArea(@PathVariable Long areaId) {
        return ResponseEntity.ok(empleadoService.findByArea(areaId));
    }

    // Empleados con acceso al sistema
    @GetMapping("/con-acceso")
    public ResponseEntity<List<Empleado>> findConAcceso() {
        return ResponseEntity.ok(empleadoService.findConAcceso());
    }

    // Empleados sin acceso al sistema
    @GetMapping("/sin-acceso")
    public ResponseEntity<List<Empleado>> findSinAcceso() {
        return ResponseEntity.ok(empleadoService.findSinAcceso());
    }

    // Crear nuevo empleado
    @PostMapping
    public ResponseEntity<Empleado> create(@RequestBody Empleado empleado) {
        return ResponseEntity.ok(empleadoService.save(empleado));
    }

    // Actualizar empleado
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> update(@PathVariable Long id, @RequestBody Empleado empleado) {
        return ResponseEntity.ok(empleadoService.update(id, empleado));
    }

    // Eliminar empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Asignar un usuario (credenciales) a un empleado
    @PutMapping("/{empleadoId}/asignar-usuario/{userId}")
    public ResponseEntity<Empleado> asignarUsuario(
            @PathVariable Long empleadoId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(empleadoService.asignarUsuario(empleadoId, userId));
    }

    // Revocar acceso al sistema
    @PutMapping("/{empleadoId}/revocar-usuario")
    public ResponseEntity<Empleado> revocarUsuario(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(empleadoService.revocarUsuario(empleadoId));
    }
}