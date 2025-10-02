package org.johnwesley.gerenciamentorifa.config;

import org.johnwesley.gerenciamentorifa.models.Usuario;
import org.johnwesley.gerenciamentorifa.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner createDefaultUsers(UsuarioRepository usuarioRepository,
                                                PasswordEncoder passwordEncoder) {
        return args -> {
            createUserIfNotExists(
                    usuarioRepository,
                    passwordEncoder,
                    "admin",
                    "admin@admin.com",
                    "admin",
                    "ADMIN"
            );

            createUserIfNotExists(
                    usuarioRepository,
                    passwordEncoder,
                    "padrao",
                    "padrao@padrao.com",
                    "padrao",
                    "USER"
            );
        };
    }

    private void createUserIfNotExists(UsuarioRepository usuarioRepository,
                                       PasswordEncoder passwordEncoder,
                                       String nome,
                                       String email,
                                       String senha,
                                       String role) {
        usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(passwordEncoder.encode(senha));
                    usuario.setRole(role);
                    return usuarioRepository.save(usuario);
                });
    }
}