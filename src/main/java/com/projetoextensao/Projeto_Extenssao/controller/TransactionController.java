package com.projetoextensao.Projeto_Extenssao.controller;

import com.projetoextensao.Projeto_Extenssao.domain.Transaction;
import com.projetoextensao.Projeto_Extenssao.dto.TransactionRequestDTO;
import com.projetoextensao.Projeto_Extenssao.dto.TransactionResponseDTO;
import com.projetoextensao.Projeto_Extenssao.jwt.JwtFilter;
import com.projetoextensao.Projeto_Extenssao.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<TransactionResponseDTO>> findAllByClientId(@PathVariable UUID clientId) {
        List<TransactionResponseDTO> transactions = transactionService.findAllByClient(clientId)
                .stream()
                .map(TransactionResponseDTO::new)
                .toList();

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable UUID id) {
        Transaction transaction = transactionService.findById(id);
        return ResponseEntity.ok(new TransactionResponseDTO(transaction));
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(
            @RequestBody @Valid TransactionRequestDTO dto,
            UriComponentsBuilder builder) {

        Transaction transaction = transactionService.create(dto);

        URI uri = builder.path("/transaction/{id}")
                .buildAndExpand(transaction.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new TransactionResponseDTO(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable UUID id,
                                                         @RequestBody @Valid TransactionRequestDTO dto) {
        Transaction updated = transactionService.update(id, dto);
        return ResponseEntity.ok(new TransactionResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
