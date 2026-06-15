package com.example.IM.Area;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {
    
    List<Area> findByActivoTrue();
    
    Optional<Area> findByNombre(String nombre);
    
    List<Area> findBySede(String sede);
    
    boolean existsByNombre(String nombre);
}