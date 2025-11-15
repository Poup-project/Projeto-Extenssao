package com.projetoextensao.Projeto_Extenssao.controller;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryRequestDTO;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryResponseDTO;
import com.projetoextensao.Projeto_Extenssao.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> insert(
            @RequestBody @Valid CategoryRequestDTO dto,
            UriComponentsBuilder builder) {
        Category category = categoryService.create(dto);

        CategoryResponseDTO response = new CategoryResponseDTO(
                category.getId(),
                category.getTitle(),
                category.getColorHex(),
                category.getClient().getId()
        );

        URI uri = builder.path("/category/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    // AGORA FILTRADO POR USUÁRIO
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAllByUser(
            @RequestParam UUID userId) {
        List<CategoryResponseDTO> categories = categoryService.findAllByUserId(userId);
        return ResponseEntity.ok(categories);
    }

    // AGORA VALIDA SE A CATEGORIA PERTENCE AO USUÁRIO
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        Category category = categoryService.findByIdAndUserId(id, userId);

        CategoryResponseDTO response = new CategoryResponseDTO(
                category.getId(),
                category.getTitle(),
                category.getColorHex(),
                category.getClient().getId()
        );
        return ResponseEntity.ok(response);
    }

    // AGORA VALIDA SE A CATEGORIA PERTENCE AO USUÁRIO
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryRequestDTO dto) {
        Category category = categoryService.update(id, dto);

        CategoryResponseDTO response = new CategoryResponseDTO(
                category.getId(),
                category.getTitle(),
                category.getColorHex(),
                category.getClient().getId()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
