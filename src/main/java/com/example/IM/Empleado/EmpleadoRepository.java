package com.example.IM.Empleado;

import com.example.IM.Area.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findByArea(Area area);

    Optional<Empleado> findByDocIdentidad(String docIdentidad);

    boolean existsByDocIdentidad(String docIdentidad);

    // Buscar empleados que tienen acceso al sistema (user no nulo)
    @Query("SELECT e FROM Empleado e WHERE e.user IS NOT NULL")
    List<Empleado> findEmpleadosConAcceso();

    // Buscar empleados sin user asignado
    @Query("SELECT e FROM Empleado e WHERE e.user IS NULL")
    List<Empleado> findEmpleadosSinAcceso();

    // Buscar empleado por user_id
    Optional<Empleado> findByUserId(Long userId);
}