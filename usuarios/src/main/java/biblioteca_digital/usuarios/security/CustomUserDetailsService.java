package biblioteca_digital.usuarios.security;

import biblioteca_digital.usuarios.model.Usuario;
import biblioteca_digital.usuarios.repository.UsuariosRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final UsuariosRepository usuariosRepository;

    public CustomUserDetailsService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // El "username" en este contexto es el email del usuario.
        Usuario usuario = usuariosRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró un usuario con el email: " + username));

        // Envuelve la entidad Usuario en un CustomUserDetails para que Spring Security la entienda.
        return new CustomUserDetails(usuario);
    }
}
