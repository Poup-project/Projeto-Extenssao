package com.projetoextensao.Projeto_Extenssao.service;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryRequestDTO;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryResponseDTO;
import com.projetoextensao.Projeto_Extenssao.repository.CategoryRepository;
import com.projetoextensao.Projeto_Extenssao.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClientRepository clientRepository;

    // MÉTODO NOVO: Buscar todas as categorias de um usuário específico
    public List<CategoryResponseDTO> findAllByUserId(UUID userId) {
        // Validar se o usuário existe
        if (!clientRepository.existsById(userId)) {
            throw new EntityNotFoundException("Cliente com ID " + userId + " não encontrado");
        }

        return categoryRepository.findByClientId(userId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // MÉTODO NOVO: Buscar categoria por ID validando se pertence ao usuário
    public Category findByIdAndUserId(UUID id, UUID userId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada"));

        // VALIDAÇÃO: Verificar se a categoria pertence ao usuário
        if (!category.getClient().getId().equals(userId)) {
            throw new RuntimeException("Categoria não pertence a este usuário!");
        }

        return category;
    }

    // MÉTODO ATUALIZADO: Criar categoria (já estava bom)
    public Category create(CategoryRequestDTO dto) {
        Client client = clientRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + dto.getUserId() + " não encontrado"));

        Category category = new Category(dto);
        category.setClient(client);

        return categoryRepository.save(category);
    }

    // MÉTODO ATUALIZADO: Update com validação de usuário
    public Category update(UUID id, CategoryRequestDTO dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada"));

        // VALIDAÇÃO: Verificar se a categoria pertence ao usuário da requisição
        if (!existing.getClient().getId().equals(dto.getUserId())) {
            throw new RuntimeException("Não é possível alterar categoria de outro usuário!");
        }

        existing.setTitle(dto.getTitle());
        existing.setColorHex(dto.getColorHex());

        // Não precisa buscar client novamente, já validamos que é o mesmo
        return categoryRepository.save(existing);
    }

    // MÉTODO ATUALIZADO: Delete com validação opcional (se quiser adicionar)
    public void delete(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada"));
        categoryRepository.delete(category);
    }

    // MÉTODO NOVO: Converter Category para CategoryResponseDTO
    public CategoryResponseDTO toResponseDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getTitle(),
                category.getColorHex(),
                category.getClient().getId()
        );
    }

    // MÉTODO MANTIDO: Para compatibilidade (se outros códigos usarem)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // MÉTODO MANTIDO: Para compatibilidade (se outros códigos usarem)
    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada"));
    }

    // MÉTODO MANTIDO: Para compatibilidade (se outros códigos usarem)
    public Category create(Category category) {
        return categoryRepository.save(category);
    }
}
