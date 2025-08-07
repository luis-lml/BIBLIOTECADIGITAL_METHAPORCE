package biblioteca_digital.usuarios.dto;

import biblioteca_digital.usuarios.model.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new user.
 * Contains strict validation rules for the user creation process.
 */
public record UsuarioCreateDTO(
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre,

    @NotBlank(message = "El apellido no puede estar vacío")
    String apellido,

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    String email,

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String password,

    @NotNull(message = "El rol no puede ser nulo")
    RolUsuario rol
) {}
