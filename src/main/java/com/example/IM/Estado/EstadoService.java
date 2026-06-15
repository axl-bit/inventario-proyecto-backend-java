package com.example.IM.Estado;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {
    
    private final EstadoRepository estadoRepository;
    
    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }
    
    public List<Estado> findActivos() {
        return estadoRepository.findByActivoTrue();
    }
    
    public Estado findById(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));
    }
    
    public Estado save(Estado estado) {
        if (estadoRepository.existsByNombre(estado.getNombre())) {
            throw new RuntimeException("Ya existe un estado con ese nombre");
        }
        return estadoRepository.save(estado);
    }
    
    public Estado update(Long id, Estado estado) {
        Estado existente = findById(id);
        existente.setNombre(estado.getNombre());
        existente.setActivo(estado.getActivo());
        return estadoRepository.save(existente);
    }
    
    public void delete(Long id) {
        Estado estado = findById(id);
        estado.setActivo(false);   // antes era 'N'
        estadoRepository.save(estado);
    }
}