package biblioteca_digital.usuarios.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;

import biblioteca_digital.usuarios.dto.UsuarioCreateDTO;
import biblioteca_digital.usuarios.dto.UsuarioRequestDTO;
import biblioteca_digital.usuarios.dto.UsuarioResponseDTO;
import biblioteca_digital.usuarios.service.UsuariosService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuariosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Integer id) {
        return usuariosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioResponseDTO createdUsuario = usuariosService.createUser(usuarioCreateDTO);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUsuario.idUsuario())
                .toUri();

        return ResponseEntity.created(location).body(createdUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuariosService.updateUser(id, usuarioRequestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        boolean isDeleted = usuariosService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // HTTP 204: Éxito, sin contenido de respuesta
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404: No encontrado
        }
    }

}
