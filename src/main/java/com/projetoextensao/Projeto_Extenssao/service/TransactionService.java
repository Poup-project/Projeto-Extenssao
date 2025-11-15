package com.projetoextensao.Projeto_Extenssao.service;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.domain.Transaction;
import com.projetoextensao.Projeto_Extenssao.dto.TransactionRequestDTO;
import com.projetoextensao.Projeto_Extenssao.dto.TransactionResponseDTO;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryResponseDTO;
import com.projetoextensao.Projeto_Extenssao.repository.CategoryRepository;
import com.projetoextensao.Projeto_Extenssao.repository.ClientRepository;
import com.projetoextensao.Projeto_Extenssao.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // MÉTODO NOVO: Buscar todas as transações de um usuário específico
    public List<TransactionResponseDTO> findAllByUserId(UUID userId) {
        // Validar se o usuário existe
        if (!clientRepository.existsById(userId)) {
            throw new EntityNotFoundException("Cliente com ID " + userId + " não encontrado");
        }

        return transactionRepository.findByClientId(userId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // MÉTODO NOVO: Buscar transação por ID validando se pertence ao usuário
    public Transaction findByIdAndUserId(UUID id, UUID userId) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação com ID " + id + " não encontrada"));

        // VALIDAÇÃO: Verificar se a transação pertence ao usuário
        if (!transaction.getClient().getId().equals(userId)) {
            throw new RuntimeException("Transação não pertence a este usuário!");
        }

        return transaction;
    }

    // MÉTODO ATUALIZADO: Create com validações de usuário e categoria
    public Transaction create(TransactionRequestDTO dto) {
        // Validar se o usuário existe
        Client client = clientRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + dto.getUserId() + " não encontrado"));

        // Validar se a categoria existe e pertence ao usuário
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + dto.getCategoryId() + " não encontrada"));

        // VALIDAÇÃO: Verificar se a categoria pertence ao usuário
        if (!category.getClient().getId().equals(dto.getUserId())) {
            throw new RuntimeException("Categoria não pertence a este usuário!");
        }

        Transaction transaction = new Transaction();
        transaction.setTitle(dto.getTitle());
        transaction.setPrice(dto.getPrice());
        transaction.setClient(client);
        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }

    // MÉTODO ATUALIZADO: Update com validações de usuário e categoria
    public Transaction update(UUID id, TransactionRequestDTO dto) {
        Transaction existingTransaction = findByIdAndUserId(id, dto.getUserId());

        // Validar se a categoria existe e pertence ao usuário
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + dto.getCategoryId() + " não encontrada"));

        // VALIDAÇÃO: Verificar se a categoria pertence ao usuário
        if (!category.getClient().getId().equals(dto.getUserId())) {
            throw new RuntimeException("Categoria não pertence a este usuário!");
        }

        existingTransaction.setTitle(dto.getTitle());
        existingTransaction.setPrice(dto.getPrice());
        existingTransaction.setCategory(category);

        return transactionRepository.save(existingTransaction);
    }

    public void delete(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação com ID " + id + " não encontrada"));
        transactionRepository.delete(transaction);
    }

    // MÉTODO NOVO: Converter Transaction para TransactionResponseDTO com Category completa
    public TransactionResponseDTO toResponseDTO(Transaction transaction) {
        Category category = transaction.getCategory();

        // Criar DTO da category completa
        CategoryResponseDTO categoryDTO = new CategoryResponseDTO(
                category.getId(),
                category.getTitle(),
                category.getColorHex(),
                category.getClient().getId()
        );

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getTitle(),
                transaction.getPrice(),
                categoryDTO, // Category COMPLETA (não apenas ID)
                transaction.getClient().getId()
        );
    }

    // MÉTODOS MANTIDOS: Para compatibilidade
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação com ID " + id + " não encontrada"));
    }
}
