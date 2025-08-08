package biblioteca_digital.usuarios.service;

import biblioteca_digital.usuarios.dto.UsuarioCreateDTO;
import biblioteca_digital.usuarios.dto.UsuarioRequestDTO;
import biblioteca_digital.usuarios.dto.UsuarioResponseDTO;
import biblioteca_digital.usuarios.exception.EmailAlreadyExistsException;
import biblioteca_digital.usuarios.model.Usuario;
import biblioteca_digital.usuarios.repository.UsuariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder) {
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuariosRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDTO> findById(Integer id) {
        return usuariosRepository.findById(id)
                .map(this::toResponseDTO);
    }

    @Transactional
    public UsuarioResponseDTO createUser(UsuarioCreateDTO createDTO) {
        if (usuariosRepository.findByEmail(createDTO.email()).isPresent()) {
            throw new EmailAlreadyExistsException("El email '" + createDTO.email() + "' ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(createDTO.nombre());
        usuario.setApellido(createDTO.apellido());
        usuario.setEmail(createDTO.email());
        usuario.setRol(createDTO.rol());

        // Password is guaranteed non-null by @NotBlank in UsuarioCreateDTO
        usuario.setPasswordHash(passwordEncoder.encode(createDTO.password()));

        Usuario savedUsuario = usuariosRepository.save(usuario);
        return toResponseDTO(savedUsuario);
    }

    @Transactional
    public Optional<UsuarioResponseDTO> updateUser(Integer id, UsuarioRequestDTO requestDTO) {
        return usuariosRepository.findById(id)
                .map(usuarioExistente -> {

                    usuarioExistente.setNombre(requestDTO.nombre());
                    usuarioExistente.setApellido(requestDTO.apellido());
                    usuarioExistente.setEmail(requestDTO.email());
                    usuarioExistente.setRol(requestDTO.rol());

                    // Solo actualiza la contraseña si se proporciona una nueva no vacía
                    if (requestDTO.password() != null && !requestDTO.password().isBlank()) {
                        usuarioExistente.setPasswordHash(passwordEncoder.encode(requestDTO.password()));
                    }

                    Usuario updatedUsuario = usuariosRepository.save(usuarioExistente);
                    return toResponseDTO(updatedUsuario);
                });
    }

    @Transactional
    public boolean deleteUser(Integer id) {
        if (usuariosRepository.existsById(id)) {
            usuariosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
