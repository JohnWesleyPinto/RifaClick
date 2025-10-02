package org.johnwesley.gerenciamentorifa.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.johnwesley.gerenciamentorifa.dto.RifaDTO;
import org.johnwesley.gerenciamentorifa.models.Bilhete;
import org.johnwesley.gerenciamentorifa.models.Rifa;
import org.johnwesley.gerenciamentorifa.repository.BilheteRepository;
import org.johnwesley.gerenciamentorifa.repository.RifaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RifaService {

    private final RifaRepository rifaRepository;
    private final BilheteRepository bilheteRepository;

    public List<Rifa> getAllRifas() {
        return rifaRepository.findAll();
    }

    public Rifa getRifa(Long id) {
        return rifaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rifa não encontrada para o id informado."));
    }

    public Boolean getStatus(Long id) {
        Rifa rifa = getRifa(id);
        return Boolean.TRUE.equals(rifa.getStatusSorteio());
    }

    public Rifa criarRifa(RifaDTO rifaDTO) {
        Rifa rifa = new Rifa();
        rifa.setNome(rifaDTO.getNome());
        rifa.setPremio(rifaDTO.getPremio());
        rifa.setQuantidadeBilhetes(rifaDTO.getQuantidadeBilhetes());
        rifa.setPrecoBilhete(rifaDTO.getPrecoBilhete());
        rifa.setDataInicio(rifaDTO.getDataInicio());
        rifa.setDataFim(rifaDTO.getDataFim());
        rifa.setStatusSorteio(Boolean.FALSE);
        return rifaRepository.save(rifa);
    }

    public Bilhete realizarSorteio(Long id) {
        Rifa rifa = getRifa(id);

        LocalDateTime dataFim = rifa.getDataFim();
        if (Objects.nonNull(dataFim) && LocalDateTime.now().isBefore(dataFim)) {
            throw new IllegalStateException("O período da rifa ainda não foi encerrado.");
        }

        List<Bilhete> bilhetesPagos = bilheteRepository.findByRifaId(id).stream()
                .filter(bilhete -> Boolean.TRUE.equals(bilhete.getStatusPagamento()))
                .collect(Collectors.toList());

        if (bilhetesPagos.isEmpty()) {
            throw new IllegalStateException("Não há bilhetes pagos disponíveis para realizar o sorteio.");
        }

        Bilhete vencedor = rifa.realizarSorteio(bilhetesPagos);
        rifaRepository.save(rifa);

        return vencedor;
    }

    public List<Rifa> listarHistorico() {
        return rifaRepository.findByStatusSorteioTrue();
    }
}