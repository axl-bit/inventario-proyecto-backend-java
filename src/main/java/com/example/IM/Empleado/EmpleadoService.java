package com.example.IM.Empleado;

import com.example.IM.Area.Area;
import com.example.IM.Area.AreaService;
import com.example.IM.User.User;
import com.example.IM.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final AreaService areaService;
    private final UserRepository userRepository;

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public Empleado findById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
    }

    public List<Empleado> findByArea(Long areaId) {
        Area area = areaService.findById(areaId);
        return empleadoRepository.findByArea(area);
    }

    public List<Empleado> findConAcceso() {
        return empleadoRepository.findEmpleadosConAcceso();
    }

    public List<Empleado> findSinAcceso() {
        return empleadoRepository.findEmpleadosSinAcceso();
    }

    @Transactional
    public Empleado save(Empleado empleado) {
        if (empleado.getDocIdentidad() != null &&
            empleadoRepository.existsByDocIdentidad(empleado.getDocIdentidad())) {
            throw new RuntimeException("Ya existe un empleado con ese documento de identidad");
        }
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public Empleado update(Long id, Empleado empleadoActualizado) {
        Empleado existente = findById(id);
        existente.setNombre(empleadoActualizado.getNombre());
        existente.setApellido(empleadoActualizado.getApellido());
        existente.setDocIdentidad(empleadoActualizado.getDocIdentidad());
        existente.setFechaAlta(empleadoActualizado.getFechaAlta());
        existente.setArea(empleadoActualizado.getArea());
        // No actualizamos la relación user aquí para mantener la integridad;
        // se hará a través de métodos específicos.
        return empleadoRepository.save(existente);
    }

    @Transactional
    public void delete(Long id) {
        Empleado empleado = findById(id);
        empleadoRepository.delete(empleado);
    }

    // Método para asignar/revocar acceso al sistema
    @Transactional
    public Empleado asignarUsuario(Long empleadoId, Long userId) {
        Empleado empleado = findById(empleadoId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User no encontrado"));
        // Verificar que el user no esté ya asignado a otro empleado
        if (empleadoRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("El usuario ya está asignado a otro empleado");
        }
        empleado.setUser(user);
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public Empleado revocarUsuario(Long empleadoId) {
        Empleado empleado = findById(empleadoId);
        empleado.setUser(null);
        return empleadoRepository.save(empleado);
    }
}