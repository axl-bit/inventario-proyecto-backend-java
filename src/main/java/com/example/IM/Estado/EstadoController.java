package com.example.IM.Estado;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estados")
@RequiredArgsConstructor
public class EstadoController {
    
    private final EstadoService estadoService;
    
    @GetMapping
    public ResponseEntity<List<Estado>> findAll() {
        return ResponseEntity.ok(estadoService.findAll());
    }
    
    @GetMapping("/activos")
    public ResponseEntity<List<Estado>> findActivos() {
        return ResponseEntity.ok(estadoService.findActivos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estado> findById(@PathVariable Long id) {
        return ResponseEntity.ok(estadoService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Estado> create(@RequestBody Estado estado) {
        return ResponseEntity.ok(estadoService.save(estado));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estado> update(@PathVariable Long id, @RequestBody Estado estado) {
        return ResponseEntity.ok(estadoService.update(id, estado));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        estadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}