package org.controller;


import lombok.RequiredArgsConstructor;
import org.dto.BilheteDTO;
import org.service.BilheteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/raffles/{raffleId}")
    public ResponseEntity<List<BilheteResponseDTO>> buscarPorRifa(@PathVariable Long raffleId) {
        List<BilheteResponseDTO> bilhetes = bilheteService.buscarPorRifa(raffleId);
        return ResponseEntity.ok(bilhetes);
    }
}
