package com.projetoextensao.Projeto_Extenssao.service;

import com.projetoextensao.Projeto_Extenssao.Exception.EmailAlreadyExistsException;
import com.projetoextensao.Projeto_Extenssao.domain.Category;
import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryRequestDTO;
import com.projetoextensao.Projeto_Extenssao.repository.CategoryRepository;
import com.projetoextensao.Projeto_Extenssao.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada"));
    }

    public List<Category> findAllByClient(UUID clientId) {
        return categoryRepository.findByClientId(clientId);
    }

    public Category create(@Valid CategoryRequestDTO dto) {

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Category category = new Category();
        category.setTitle(dto.getTitle());
        category.setColorHex(dto.getColorHex());
        category.setClient(client);

        return categoryRepository.save(category);
    }

    public Category update(UUID id, CategoryRequestDTO dto, UUID clientId) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada"));

        if (!existing.getClient().getId().equals(clientId)) {
            throw new RuntimeException("Você não tem permissão para editar esta categoria");
        }

        existing.setTitle(dto.getTitle());
        existing.setColorHex(dto.getColorHex());

        return categoryRepository.save(existing);
    }

    public void delete(UUID id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}