package org.johnwesley.gerenciamentorifa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.johnwesley.gerenciamentorifa.dto.LoginRequest;
import org.johnwesley.gerenciamentorifa.dto.RegisterRequest;
import org.johnwesley.gerenciamentorifa.jwt.JwtTokenProvider;
import org.johnwesley.gerenciamentorifa.models.Usuario;
import org.johnwesley.gerenciamentorifa.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login (@Valid @RequestBody LoginRequest loginResquest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginResquest.getEmail(), loginResquest.getSenha())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(loginResquest.getEmail());
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensagem", "Credenciais inválidas"));
        }

        Usuario usuario = usuarioOptional.get();
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", usuario.getId());
        userData.put("nome", usuario.getNome());
        userData.put("email", usuario.getEmail());
        userData.put("role", usuario.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", userData);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest registerRequest){
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(registerRequest.getEmail());
        if (usuarioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensagem", "E-mail já cadastrado"));
        }

        Usuario usuario = new Usuario();
        usuario.setNome(registerRequest.getNome());
        usuario.setEmail(registerRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(registerRequest.getSenha()));
        usuario.setRole("ROLE_USER");

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensagem", "Usuário registrado com sucesso",
                        "usuarioId", usuarioSalvo.getId()
                ));
    }
}

