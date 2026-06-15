package com.example.IM.Accesorio;

import com.example.IM.Estado.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccesorioRepository extends JpaRepository<Accesorio, Long> {

    List<Accesorio> findByActivoTrue();

    List<Accesorio> findByEstado(Estado estado);

    boolean existsByNombre(String nombre);
}