package com.example.IM.Accesorio;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accesorios")
@RequiredArgsConstructor
public class AccesorioController {

    private final AccesorioService accesorioService;

    @GetMapping
    public ResponseEntity<List<Accesorio>> findAll() {
        return ResponseEntity.ok(accesorioService.findAll());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Accesorio>> findActivos() {
        return ResponseEntity.ok(accesorioService.findActivos());
    }

    @GetMapping("/estado/{estadoId}")
    public ResponseEntity<List<Accesorio>> findByEstado(@PathVariable Long estadoId) {
        return ResponseEntity.ok(accesorioService.findByEstado(estadoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accesorio> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accesorioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Accesorio> create(@RequestBody Accesorio accesorio) {
        return ResponseEntity.ok(accesorioService.save(accesorio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accesorio> update(@PathVariable Long id, @RequestBody Accesorio accesorio) {
        return ResponseEntity.ok(accesorioService.update(id, accesorio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accesorioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}