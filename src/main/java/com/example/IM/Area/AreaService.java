package com.example.IM.Area;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {
    
    private final AreaRepository areaRepository;
    
    public List<Area> findAll() {
        return areaRepository.findAll();
    }
    
    public List<Area> findActivos() {
        return areaRepository.findByActivo('S');
    }
    
    public List<Area> findBySede(String sede) {
        return areaRepository.findBySede(sede);
    }
    
    public Area findById(Long id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + id));
    }
    
    public Area save(Area area) {
        if (areaRepository.existsByNombre(area.getNombre())) {
            throw new RuntimeException("Ya existe un área con ese nombre");
        }
        return areaRepository.save(area);
    }
    
    public Area update(Long id, Area area) {
        Area existente = findById(id);
        existente.setNombre(area.getNombre());
        existente.setSede(area.getSede());
        existente.setActivo(area.getActivo());
        return areaRepository.save(existente);
    }
    
    public void delete(Long id) {
        Area area = findById(id);
        area.setActivo('N');
        areaRepository.save(area);
    }
}