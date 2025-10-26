package com.projetoextensao.Projeto_Extenssao.repository;

import com.projetoextensao.Projeto_Extenssao.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
