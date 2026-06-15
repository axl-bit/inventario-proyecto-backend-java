package com.example.IM.TipoEquipo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TipoEquipoRepository extends JpaRepository<TipoEquipo, Long> {

    List<TipoEquipo> findByActivoTrue();

    Optional<TipoEquipo> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}