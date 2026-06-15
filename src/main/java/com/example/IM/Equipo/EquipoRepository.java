package com.example.IM.Equipo;

import com.example.IM.Empleado.Empleado;
import com.example.IM.Estado.Estado;
import com.example.IM.TipoEquipo.TipoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByActivoTrue();

    List<Equipo> findByEmpleado(Empleado empleado);

    List<Equipo> findByEstado(Estado estado);

    List<Equipo> findByTipoEquipo(TipoEquipo tipoEquipo);

    Optional<Equipo> findBySerie(String serie);

    Optional<Equipo> findByImei(String imei);

    boolean existsBySerie(String serie);

    boolean existsByImei(String imei);

    // Equipos activos por empleado
    List<Equipo> findByEmpleadoAndActivoTrue(Empleado empleado);

    // Búsqueda por marca
    List<Equipo> findByMarcaContainingIgnoreCase(String marca);

    // Búsqueda por modelo
    List<Equipo> findByModeloContainingIgnoreCase(String modelo);

    // Búsqueda combinada
    @Query("SELECT e FROM Equipo e WHERE " +
           "(:marca IS NULL OR LOWER(e.marca) LIKE LOWER(CONCAT('%', :marca, '%'))) AND " +
           "(:modelo IS NULL OR LOWER(e.modelo) LIKE LOWER(CONCAT('%', :modelo, '%'))) AND " +
           "(:empleadoId IS NULL OR e.empleado.id = :empleadoId) AND " +
           "(:estadoId IS NULL OR e.estado.id = :estadoId) AND " +
           "(:tipoEquipoId IS NULL OR e.tipoEquipo.id = :tipoEquipoId)")
    List<Equipo> buscarConFiltros(
            @Param("marca") String marca,
            @Param("modelo") String modelo,
            @Param("empleadoId") Long empleadoId,
            @Param("estadoId") Long estadoId,
            @Param("tipoEquipoId") Long tipoEquipoId);
}