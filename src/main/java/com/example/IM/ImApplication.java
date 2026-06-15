package com.example.IM;

import com.example.IM.User.*;
import com.example.IM.Estado.*;
import com.example.IM.Area.*;
import com.example.IM.TipoEquipo.*;
import com.example.IM.Accesorio.*;
import com.example.IM.Empleado.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ImApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(
            EstadoRepository estadoRepository,
            AreaRepository areaRepository,
            TipoEquipoRepository tipoEquipoRepository,
            AccesorioRepository accesorioRepository,
            EmpleadoRepository empleadoRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Solo insertar si las tablas están vacías
            if (estadoRepository.count() == 0) {
                estadoRepository.save(new Estado(null, "Operativo", true));
                estadoRepository.save(new Estado(null, "En reparación", true));
                estadoRepository.save(new Estado(null, "Baja", true));
                estadoRepository.save(new Estado(null, "Almacenado", true));
            }

            if (areaRepository.count() == 0) {
                areaRepository.save(new Area(null, "Sistemas", "Lima", true));
                areaRepository.save(new Area(null, "Recursos Humanos", "Lima", true));
                areaRepository.save(new Area(null, "Contabilidad", "Arequipa", true));
                areaRepository.save(new Area(null, "Logística", "Trujillo", true));
            }

            if (tipoEquipoRepository.count() == 0) {
                tipoEquipoRepository.save(new TipoEquipo(null, "Laptop", true, true));
                tipoEquipoRepository.save(new TipoEquipo(null, "Desktop", false, true));
                tipoEquipoRepository.save(new TipoEquipo(null, "Tablet", true, true));
                tipoEquipoRepository.save(new TipoEquipo(null, "Impresora", false, true));
            }

            if (accesorioRepository.count() == 0) {
                Estado operativo = estadoRepository.findByNombre("Operativo").orElse(null);
                accesorioRepository.save(new Accesorio(null, "Mouse", operativo, true));
                accesorioRepository.save(new Accesorio(null, "Teclado", operativo, true));
                accesorioRepository.save(new Accesorio(null, "Monitor", operativo, true));
                accesorioRepository.save(new Accesorio(null, "Cargador", operativo, true));
            }

            if (userRepository.count() == 0) {
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .email("admin@inventario.com")
                        .firstName("Admin")
                        .lastName("Sistema")
                        .role(Role.ADMIN)
                        .enabled(true)
                        .build();
                userRepository.save(admin);
            }

            if (empleadoRepository.count() == 0) {
                Area sistemas = areaRepository.findByNombre("Sistemas").orElse(null);
                Area rrhh = areaRepository.findByNombre("Recursos Humanos").orElse(null);
                Area cont = areaRepository.findByNombre("Contabilidad").orElse(null);
                User admin = userRepository.findByUsername("admin").orElse(null);

                empleadoRepository.save(Empleado.builder()
                        .nombre("Juan").apellido("Pérez").docIdentidad("12345678")
                        .area(sistemas).user(admin).build());
                empleadoRepository.save(Empleado.builder()
                        .nombre("María").apellido("Gómez").docIdentidad("87654321")
                        .area(rrhh).build());
                empleadoRepository.save(Empleado.builder()
                        .nombre("Carlos").apellido("López").docIdentidad("11223344")
                        .area(cont).build());
            }
        };
    }
}