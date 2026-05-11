package com.example.IM.Area;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaController {
    
    private final AreaService areaService;
    
    @GetMapping
    public ResponseEntity<List<Area>> findAll() {
        return ResponseEntity.ok(areaService.findAll());
    }
    
    @GetMapping("/activos")
    public ResponseEntity<List<Area>> findActivos() {
        return ResponseEntity.ok(areaService.findActivos());
    }
    
    @GetMapping("/sede/{sede}")
    public ResponseEntity<List<Area>> findBySede(@PathVariable String sede) {
        return ResponseEntity.ok(areaService.findBySede(sede));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Area> findById(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Area> create(@RequestBody Area area) {
        return ResponseEntity.ok(areaService.save(area));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Area> update(@PathVariable Long id, @RequestBody Area area) {
        return ResponseEntity.ok(areaService.update(id, area));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        areaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}