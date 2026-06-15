package com.example.IM.HistoricoAsignacion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoAsignacionService {

    private final HistoricoAsignacionRepository historicoRepository;

    public List<HistoricoAsignacion> findAll() {
        return historicoRepository.findAll();
    }

    public List<HistoricoAsignacion> findHistorialByEquipo(Long equipoId) {
        return historicoRepository.findByEquipoIdOrderByFechaAsignacionDesc(equipoId);
    }

    public List<HistoricoAsignacion> findHistorialByEmpleado(Long empleadoId) {
        return historicoRepository.findByEmpleadoIdOrderByFechaAsignacionDesc(empleadoId);
    }

    public List<HistoricoAsignacion> findEquiposActivosByEmpleado(Long empleadoId) {
        return historicoRepository.findByEmpleadoIdAndFechaDevolucionIsNull(empleadoId);
    }

    @Transactional
    public HistoricoAsignacion save(HistoricoAsignacion historico) {
        return historicoRepository.save(historico);
    }

    @Transactional
    public void registrarDevolucion(Long equipoId) {
        historicoRepository.findByEquipoIdAndFechaDevolucionIsNull(equipoId)
                .ifPresent(historico -> {
                    historico.setFechaDevolucion(LocalDateTime.now());
                    historicoRepository.save(historico);
                });
    }
}