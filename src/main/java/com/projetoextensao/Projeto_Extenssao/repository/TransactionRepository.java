package com.projetoextensao.Projeto_Extenssao.repository;

import com.projetoextensao.Projeto_Extenssao.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByClientId(UUID clientId);
}
