package org.controller;


import lombok.RequiredArgsConstructor;
import org.dto.RifaDTO;
import org.models.Bilhete;
import org.models.Rifa;
import org.service.RifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RifaController {

    private final RifaService rifaService;

    @GetMapping("/rifas")
    public List<Rifa> getAllRifas() {
        return rifaService.getAllRifas();
    }

    @GetMapping("/rifas/{id}")
    public ResponseEntity<Rifa> getRifa(@PathVariable Long id){
        return ResponseEntity.ok(rifaService.getStatus(id));
    }
    @GetMapping("/rifas/{id}/status")
    public ResponseEntity<Boolean> getStatus(@PathVariable Long id){
        return ResponseEntity.ok(rifaService.getStatus(id));
    }
    @PostMapping("/rifas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Rifa> criarRifa(@RequestBody @Validated RifaDTO rifaDTO) {
        Rifa rifa = rifaService.criarRifa(rifaDTO);
        return new ResponseEntity<>(rifa, HttpStatus.CREATED);
    }
    @PostMapping("/rifas/{id}/draw")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Bilhete> realizarSorteio(@PathVariable Long id) {
        Bilhete vencedor = rifaService.realizarSorteio(id);
        return ResponseEntity.ok(vencedor);
    }

    @GetMapping("/raffles/history")
    public ResponseEntity<List<Rifa>> listarHistorico() {
        return ResponseEntity.ok(rifaService.listarHistorico());
    }
}
