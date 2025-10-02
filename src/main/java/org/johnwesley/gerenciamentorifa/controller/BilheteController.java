package org.johnwesley.gerenciamentorifa.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.johnwesley.gerenciamentorifa.dto.BilheteDTO;
import org.johnwesley.gerenciamentorifa.dto.BilheteResponseDTO;
import org.johnwesley.gerenciamentorifa.service.BilheteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bilhetes")
@RequiredArgsConstructor
public class BilheteController {

    private final BilheteService bilheteService;

    @PostMapping
    public ResponseEntity<BilheteResponseDTO> comprar(@RequestBody @Valid BilheteDTO bilheteDTO) {
        BilheteResponseDTO responseDTO = bilheteService.comprarBilhete(bilheteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BilheteResponseDTO> buscarPorId(@PathVariable Long id) {
        BilheteResponseDTO responseDTO = bilheteService.buscarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/rifas/{rifaId}")
    public ResponseEntity<List<BilheteResponseDTO>> buscarPorRifa(@PathVariable Long rifaId) {
        List<BilheteResponseDTO> bilhetes = bilheteService.buscarPorRifa(rifaId);
        return ResponseEntity.ok(bilhetes);
    }
}
