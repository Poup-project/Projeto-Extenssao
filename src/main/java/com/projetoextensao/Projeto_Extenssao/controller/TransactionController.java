package com.projetoextensao.Projeto_Extenssao.controller;

import com.projetoextensao.Projeto_Extenssao.domain.Transaction;
import com.projetoextensao.Projeto_Extenssao.dto.TransactionRequestDTO;
import com.projetoextensao.Projeto_Extenssao.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable UUID id) {
        Transaction transaction = transactionService.findById(id);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody @Valid TransactionRequestDTO dto) {
        Transaction created = transactionService.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable UUID id,
                                              @RequestBody @Valid TransactionRequestDTO dto) {
        Transaction updated = transactionService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
