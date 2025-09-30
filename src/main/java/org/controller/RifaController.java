package org.controller;


import lombok.RequiredArgsConstructor;
import org.dto.RifaDTO;
import org.models.Rifa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rifas")
@RequiredArgsConstructor
public class RifaController {

    @Autowired
    private final RifaService rifaService;

    @GetMapping
    public List<Rifa> getAllRifas() {
        return rifaService.getAllRifas();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Rifa> criarRifa(@RequestBody @Validated RifaDTO rifaDTO) {
        Rifa rifa = rifaService.criarRifa(RifaDTO);
        return new ResponseEntity<>(rifa, HttpStatus.CREATED);
    }
}
