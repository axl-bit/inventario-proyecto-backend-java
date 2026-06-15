package com.example.IM.Equipo;

import com.example.IM.Empleado.Empleado;
import com.example.IM.Empleado.EmpleadoService;
import com.example.IM.Estado.Estado;
import com.example.IM.Estado.EstadoService;
import com.example.IM.HistoricoAsignacion.HistoricoAsignacion;
import com.example.IM.HistoricoAsignacion.HistoricoAsignacionService;
import com.example.IM.TipoEquipo.TipoEquipo;
import com.example.IM.TipoEquipo.TipoEquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final EmpleadoService empleadoService;
    private final TipoEquipoService tipoEquipoService;
    private final EstadoService estadoService;
    private final HistoricoAsignacionService historicoService;

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    public List<Equipo> findActivos() {
        return equipoRepository.findByActivoTrue();
    }

    public Equipo findById(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con ID: " + id));
    }

    public List<Equipo> findByEmpleado(Long empleadoId) {
        Empleado empleado = empleadoService.findById(empleadoId);
        return equipoRepository.findByEmpleado(empleado);
    }

    public List<Equipo> findByEstado(Long estadoId) {
        Estado estado = estadoService.findById(estadoId);
        return equipoRepository.findByEstado(estado);
    }

    public List<Equipo> findByTipoEquipo(Long tipoEquipoId) {
        TipoEquipo tipoEquipo = tipoEquipoService.findById(tipoEquipoId);
        return equipoRepository.findByTipoEquipo(tipoEquipo);
    }

    public List<Equipo> buscarConFiltros(String marca, String modelo, Long empleadoId,
                                          Long estadoId, Long tipoEquipoId) {
        return equipoRepository.buscarConFiltros(marca, modelo, empleadoId, estadoId, tipoEquipoId);
    }

    @Transactional
    public Equipo save(Equipo equipo) {
        validarEquipo(equipo);
        equipo.setFechaAlta(LocalDateTime.now());
        
        // Asegurar lista de accesorios no nula
        if (equipo.getAccesorios() == null) {
            equipo.setAccesorios(new ArrayList<>());
        }
        
        Equipo equipoGuardado = equipoRepository.save(equipo);
        registrarHistorico(equipoGuardado, equipo.getEmpleado(), "Asignación inicial");
        return equipoGuardado;
    }

    @Transactional
    public Equipo update(Long id, Equipo equipoActualizado) {
        Equipo existente = findById(id);

        // Actualizar campos básicos
        existente.setMarca(equipoActualizado.getMarca());
        existente.setModelo(equipoActualizado.getModelo());
        existente.setSistemaOperativo(equipoActualizado.getSistemaOperativo());
        existente.setProcesador(equipoActualizado.getProcesador());
        existente.setRam(equipoActualizado.getRam());
        existente.setAlmacenamiento(equipoActualizado.getAlmacenamiento());
        existente.setImei(equipoActualizado.getImei());
        existente.setSerie(equipoActualizado.getSerie());
        existente.setObservaciones(equipoActualizado.getObservaciones());
        existente.setFechaIngreso(equipoActualizado.getFechaIngreso());
        existente.setRenovacion(equipoActualizado.getRenovacion());
        existente.setActivo(equipoActualizado.getActivo());
        existente.setTipoEquipo(equipoActualizado.getTipoEquipo());
        existente.setEstado(equipoActualizado.getEstado());

        // Actualizar lista de accesorios (muchos a muchos)
        if (equipoActualizado.getAccesorios() != null) {
            existente.getAccesorios().clear();
            existente.getAccesorios().addAll(equipoActualizado.getAccesorios());
        }

        // Si cambió el empleado, registrar en histórico
        if (!existente.getEmpleado().getId().equals(equipoActualizado.getEmpleado().getId())) {
            historicoService.registrarDevolucion(existente.getId());
            existente.setEmpleado(equipoActualizado.getEmpleado());
            registrarHistorico(existente, equipoActualizado.getEmpleado(), "Reasignación");
        } else {
            existente.setEmpleado(equipoActualizado.getEmpleado());
        }

        return equipoRepository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        Equipo equipo = findById(id);
        equipo.setActivo(false);
        equipo.setFechaBaja(LocalDateTime.now());
        historicoService.registrarDevolucion(equipo.getId());
        equipoRepository.save(equipo);
    }

    @Transactional
    public Equipo asignarEmpleado(Long equipoId, Long empleadoId, String observaciones) {
        Equipo equipo = findById(equipoId);
        Empleado empleado = empleadoService.findById(empleadoId);

        historicoService.registrarDevolucion(equipoId);
        equipo.setEmpleado(empleado);
        equipoRepository.save(equipo);
        registrarHistorico(equipo, empleado, observaciones);

        return equipo;
    }

    @Transactional
    public Equipo devolverEquipo(Long equipoId, String observaciones) {
        Equipo equipo = findById(equipoId);
        historicoService.registrarDevolucion(equipoId);
        equipo.setFechaBaja(LocalDateTime.now());
        equipo.setActivo(false);
        return equipoRepository.save(equipo);
    }

    private void validarEquipo(Equipo equipo) {
        if (equipo.getSerie() != null && equipoRepository.existsBySerie(equipo.getSerie())) {
            throw new RuntimeException("Ya existe un equipo con esa serie: " + equipo.getSerie());
        }
        if (equipo.getImei() != null && equipoRepository.existsByImei(equipo.getImei())) {
            throw new RuntimeException("Ya existe un equipo con ese IMEI: " + equipo.getImei());
        }
    }

    private void registrarHistorico(Equipo equipo, Empleado empleado, String observaciones) {
        HistoricoAsignacion historico = HistoricoAsignacion.builder()
                .equipo(equipo)
                .empleado(empleado)
                .fechaAsignacion(LocalDateTime.now())
                .observaciones(observaciones)
                .build();
        historicoService.save(historico);
    }
}