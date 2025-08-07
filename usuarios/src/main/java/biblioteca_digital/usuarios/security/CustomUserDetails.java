package biblioteca_digital.usuarios.security;

import biblioteca_digital.usuarios.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return usuario.getPasswordHash(); 
    }

    @Override
    public String getUsername() {
        return usuario.getEmail(); 
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Puedes añadir lógica si manejas expiración de cuentas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Puedes añadir lógica para cuentas bloqueadas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Puedes añadir lógica para expiración de credenciales
    }

    @Override
    public boolean isEnabled() {
        // Por defecto, se asume que el usuario está habilitado.
        return true;
    }
}
