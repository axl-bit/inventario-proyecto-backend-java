package com.example.IM.Accesorio;

import com.example.IM.Estado.Estado;
import com.example.IM.Estado.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccesorioService {

    private final AccesorioRepository accesorioRepository;
    private final EstadoService estadoService;

    public List<Accesorio> findAll() {
        return accesorioRepository.findAll();
    }

    public List<Accesorio> findActivos() {
        return accesorioRepository.findByActivoTrue();
    }

    public List<Accesorio> findByEstado(Long estadoId) {
        Estado estado = estadoService.findById(estadoId);
        return accesorioRepository.findByEstado(estado);
    }

    public Accesorio findById(Long id) {
        return accesorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accesorio no encontrado con ID: " + id));
    }

    @Transactional
    public Accesorio save(Accesorio accesorio) {
        if (accesorioRepository.existsByNombre(accesorio.getNombre())) {
            throw new RuntimeException("Ya existe un accesorio con ese nombre");
        }
        return accesorioRepository.save(accesorio);
    }

    @Transactional
    public Accesorio update(Long id, Accesorio accesorioActualizado) {
        Accesorio existente = findById(id);
        existente.setNombre(accesorioActualizado.getNombre());
        existente.setEstado(accesorioActualizado.getEstado());
        existente.setActivo(accesorioActualizado.getActivo());
        return accesorioRepository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        Accesorio accesorio = findById(id);
        accesorio.setActivo(false);
        accesorioRepository.save(accesorio);
    }
}