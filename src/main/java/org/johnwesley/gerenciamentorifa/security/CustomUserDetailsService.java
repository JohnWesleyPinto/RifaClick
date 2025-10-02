package org.johnwesley.gerenciamentorifa.security;

import org.johnwesley.gerenciamentorifa.models.Usuario;
import org.johnwesley.gerenciamentorifa.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        Collection<? extends GrantedAuthority> authorities = buildAuthorities(usuario.getRole());

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(authorities)
                .build();
    }

    private Collection<? extends GrantedAuthority> buildAuthorities(String role) {
        if (role == null || role.isBlank()) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}