package com.example.IM.TipoEquipo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-equipo")
@RequiredArgsConstructor
public class TipoEquipoController {

    private final TipoEquipoService tipoEquipoService;

    @GetMapping
    public ResponseEntity<List<TipoEquipo>> findAll() {
        return ResponseEntity.ok(tipoEquipoService.findAll());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<TipoEquipo>> findActivos() {
        return ResponseEntity.ok(tipoEquipoService.findActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEquipo> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoEquipoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TipoEquipo> create(@RequestBody TipoEquipo tipoEquipo) {
        return ResponseEntity.ok(tipoEquipoService.save(tipoEquipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEquipo> update(@PathVariable Long id, @RequestBody TipoEquipo tipoEquipo) {
        return ResponseEntity.ok(tipoEquipoService.update(id, tipoEquipo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoEquipoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}