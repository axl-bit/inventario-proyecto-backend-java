package com.example.IM.TipoEquipo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoEquipoService {

    private final TipoEquipoRepository tipoEquipoRepository;

    public List<TipoEquipo> findAll() {
        return tipoEquipoRepository.findAll();
    }

    public List<TipoEquipo> findActivos() {
        return tipoEquipoRepository.findByActivoTrue();
    }

    public TipoEquipo findById(Long id) {
        return tipoEquipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de equipo no encontrado con ID: " + id));
    }

    @Transactional
    public TipoEquipo save(TipoEquipo tipoEquipo) {
        if (tipoEquipoRepository.existsByNombre(tipoEquipo.getNombre())) {
            throw new RuntimeException("Ya existe un tipo de equipo con ese nombre");
        }
        return tipoEquipoRepository.save(tipoEquipo);
    }

    @Transactional
    public TipoEquipo update(Long id, TipoEquipo tipoEquipoActualizado) {
        TipoEquipo existente = findById(id);
        existente.setNombre(tipoEquipoActualizado.getNombre());
        existente.setEsMovil(tipoEquipoActualizado.getEsMovil());
        existente.setActivo(tipoEquipoActualizado.getActivo());
        return tipoEquipoRepository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        TipoEquipo tipoEquipo = findById(id);
        tipoEquipo.setActivo(false);
        tipoEquipoRepository.save(tipoEquipo);
    }
}