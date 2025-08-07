package biblioteca_digital.usuarios.dto;

import biblioteca_digital.usuarios.model.RolUsuario;

public record UsuarioResponseDTO(
        Integer idUsuario,
        String nombre,
        String apellido,
        String email,
        RolUsuario rol
) {}
