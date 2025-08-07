package biblioteca_digital.usuarios.config;

import biblioteca_digital.usuarios.model.RolUsuario;
import biblioteca_digital.usuarios.model.Usuario;
import biblioteca_digital.usuarios.repository.UsuariosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initDatabase(UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuariosRepository.count() == 0) {
                log.info("Base de datos vacía. Creando usuarios iniciales...");

               
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setApellido("User");
                admin.setEmail("admin@biblioteca.com");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setRol(RolUsuario.ADMIN);

                Usuario lector = new Usuario();
                lector.setNombre("Lector");
                lector.setApellido("User");
                lector.setEmail("lector@biblioteca.com");
                lector.setPasswordHash(passwordEncoder.encode("lector123"));
                lector.setRol(RolUsuario.LECTOR);

                usuariosRepository.saveAll(List.of(admin, lector));

                log.info("Usuarios iniciales creados exitosamente.");
            } else {
                log.info("La base de datos ya contiene datos. No se requiere inicialización.");
            }
        };
    }
}
