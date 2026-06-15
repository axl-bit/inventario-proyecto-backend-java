package com.example.IM.HistoricoAsignacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface HistoricoAsignacionRepository extends JpaRepository<HistoricoAsignacion, Long> {

    // Historial de un equipo específico
    List<HistoricoAsignacion> findByEquipoIdOrderByFechaAsignacionDesc(Long equipoId);

    // Historial de un empleado
    List<HistoricoAsignacion> findByEmpleadoIdOrderByFechaAsignacionDesc(Long empleadoId);

    // Asignación activa de un equipo (sin fecha de devolución)
    Optional<HistoricoAsignacion> findByEquipoIdAndFechaDevolucionIsNull(Long equipoId);

    // Equipos actualmente asignados a un empleado
    List<HistoricoAsignacion> findByEmpleadoIdAndFechaDevolucionIsNull(Long empleadoId);

    // Buscar por equipo y empleado activos
    @Query("SELECT h FROM HistoricoAsignacion h WHERE h.equipo.id = :equipoId " +
           "AND h.empleado.id = :empleadoId AND h.fechaDevolucion IS NULL")
    Optional<HistoricoAsignacion> findActivaByEquipoAndEmpleado(
            @Param("equipoId") Long equipoId,
            @Param("empleadoId") Long empleadoId);
}