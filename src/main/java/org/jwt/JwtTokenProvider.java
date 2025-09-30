package org.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtTokenProvider {
    private final String SECRET_KEY = "secretkey";

    //todo
    public String generateToken(UserDetails userDetails) {
        //Lógica para gerar um token JWT
    }

    //todo
    public boolean validateToken(String token) {
        // Lógica para validar o token JWT
    }

    //todo
    public String getNomePeloToken(String token) {
        //logica para extrair o nome de usuario do token
    }
}
