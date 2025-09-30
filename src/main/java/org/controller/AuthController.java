package org.controller;

import org.jwt.JwtTokenProvider;
import org.springframework.aot.hint.annotation.RegisterReflection;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    //todo
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginResquest){
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        //todo
        return null;
    }
}

