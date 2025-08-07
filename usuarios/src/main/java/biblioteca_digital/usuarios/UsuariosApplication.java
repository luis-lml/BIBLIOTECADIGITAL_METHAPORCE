package biblioteca_digital.usuarios;

import biblioteca_digital.usuarios.repository.UsuariosRepository;
import biblioteca_digital.usuarios.security.CustomUserDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
public class UsuariosApplication {

	private final UsuariosRepository usuariosRepository;

	public UsuariosApplication(UsuariosRepository usuariosRepository) {
		this.usuariosRepository = usuariosRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(UsuariosApplication.class, args);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService(usuariosRepository);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize
						// Endpoints públicos (Swagger y creación de usuarios)
						.requestMatchers("/usuarios/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
						// Endpoints de Admin (requieren autenticación y rol ADMIN)
						.requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")
						.anyRequest().authenticated() // Todas las demás peticiones requieren autenticación
				)
				.httpBasic(withDefaults());
		return http.build();
	}
}
