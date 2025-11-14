package com.projetoextensao.Projeto_Extenssao.controller;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryRequestDTO;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryResponseDTO;
import com.projetoextensao.Projeto_Extenssao.jwt.JwtFilter;
import com.projetoextensao.Projeto_Extenssao.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(
            @RequestBody @Valid CategoryRequestDTO dto,
            UriComponentsBuilder builder) {

        UUID clientId = JwtFilter.getCurrentClientId();

        Category category = categoryService.create(dto, clientId);

        URI uri = builder.path("/category/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoryResponseDTO(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAllByClient() {
        UUID clientId = JwtFilter.getCurrentClientId();
        List<CategoryResponseDTO> categories = categoryService.findAllByClient(clientId)
                .stream()
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable UUID id) {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(new CategoryResponseDTO(category));
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryRequestDTO dto) {

        UUID clientId = JwtFilter.getCurrentClientId();

        Category updated = categoryService.update(id, dto, clientId);

        return ResponseEntity.ok(new CategoryResponseDTO(updated));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
