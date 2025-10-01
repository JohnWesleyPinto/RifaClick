package org.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.dto.BilheteDTO;
import org.dto.BilheteResponseDTO;
import org.models.Bilhete;
import org.models.Rifa;
import org.models.Usuario;
import org.repository.BilheteRepository;
import org.repository.RifaRepository;
import org.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BilheteService {

    private final BilheteRepository bilheteRepository;
    private final RifaRepository rifaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public BilheteResponseDTO comprarBilhete(BilheteDTO bilheteDTO) {
        validarPagamento(bilheteDTO);

        Rifa rifa = rifaRepository.findById(bilheteDTO.getRifaId())
                .orElseThrow(() -> new EntityNotFoundException("Rifa não encontrada"));

        atualizarEncerramentoPorData(rifa);
        garantirRifaAtiva(rifa);

        long bilhetesVendidos = bilheteRepository.countByRifaId(rifa.getId());
        if (bilhetesVendidos >= rifa.getQuantidadeBilhetes()) {
            encerrarRifa(rifa);
            throw new IllegalStateException("Todos os bilhetes desta rifa já foram vendidos");
        }

        Usuario usuario = usuarioRepository.findById(bilheteDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Bilhete bilhete = new Bilhete();
        bilhete.setNumero(gerarNumeroBilhete(rifa, bilhetesVendidos + 1));
        bilhete.setStatusPagamento(bilheteDTO.getStatusPagamento());
        bilhete.setUsuario(usuario);
        bilhete.setRifa(rifa);

        Bilhete bilheteSalvo = bilheteRepository.save(bilhete);

        if (bilhetesVendidos + 1 >= rifa.getQuantidadeBilhetes()) {
            encerrarRifa(rifa);
        }

        return mapearParaDTO(bilheteSalvo);
    }

    @Transactional(readOnly = true)
    public BilheteResponseDTO buscarPorId(Long id) {
        Bilhete bilhete = bilheteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bilhete não encontrado"));
        return mapearParaDTO(bilhete);
    }

    @Transactional(readOnly = true)
    public List<BilheteResponseDTO> buscarPorRifa(Long rifaId) {
        rifaRepository.findById(rifaId)
                .orElseThrow(() -> new EntityNotFoundException("Rifa não encontrada"));

        return bilheteRepository.findByRifaId(rifaId)
                .stream()
                .map(this::mapearParaDTO)
                .collect(Collectors.toList());
    }

    private void validarPagamento(BilheteDTO bilheteDTO) {
        if (bilheteDTO == null || !Boolean.TRUE.equals(bilheteDTO.getStatusPagamento())) {
            throw new IllegalArgumentException("Pagamento não confirmado para o bilhete");
        }
    }

    private void garantirRifaAtiva(Rifa rifa) {
        if (Boolean.TRUE.equals(rifa.getStatusSorteio())) {
            throw new IllegalStateException("A rifa já foi encerrada");
        }
    }

    private void atualizarEncerramentoPorData(Rifa rifa) {
        LocalDateTime dataFim = rifa.getDataFim();
        if (dataFim != null && LocalDateTime.now().isAfter(dataFim)) {
            encerrarRifa(rifa);
            throw new IllegalStateException("A rifa já foi encerrada pelo organizador");
        }
    }

    private void encerrarRifa(Rifa rifa) {
        if (!Boolean.TRUE.equals(rifa.getStatusSorteio())) {
            rifa.setStatusSorteio(true);
            if (rifa.getDataFim() == null || rifa.getDataFim().isAfter(LocalDateTime.now())) {
                rifa.setDataFim(LocalDateTime.now());
            }
            rifaRepository.save(rifa);
        }
    }

    private String gerarNumeroBilhete(Rifa rifa, long sequencial) {
        return String.format("%d-%05d", rifa.getId(), sequencial);
    }

    private BilheteResponseDTO mapearParaDTO(Bilhete bilhete) {
        return BilheteResponseDTO.builder()
                .id(bilhete.getId())
                .numero(bilhete.getNumero())
                .statusPagamento(bilhete.getStatusPagamento())
                .usuarioId(bilhete.getUsuario() != null ? bilhete.getUsuario().getId() : null)
                .rifaId(bilhete.getRifa() != null ? bilhete.getRifa().getId() : null)
                .build();
    }
}