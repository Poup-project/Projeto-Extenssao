package com.projetoextensao.Projeto_Extenssao.service;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryRequestDTO;
import com.projetoextensao.Projeto_Extenssao.repository.CategoryRepository;
import com.projetoextensao.Projeto_Extenssao.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada"));
    }

    @Transactional
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category create(CategoryRequestDTO dto) {
        Client client = clientRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + dto.getUserId() + " não encontrado"));

        Category category = new Category(dto);
        category.setClient(client);

        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(UUID id, CategoryRequestDTO dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada"));

        existing.setTitle(dto.getTitle());
        existing.setColorHex(dto.getColorHex());

        Client client = clientRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + dto.getUserId() + " não encontrado"));
        existing.setClient(client);

        return categoryRepository.save(existing);
    }

    @Transactional
    public void delete(UUID id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
