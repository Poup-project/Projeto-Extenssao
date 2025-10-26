package com.projetoextensao.Projeto_Extenssao.controller;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryRequestDTO;
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
    public ResponseEntity<Category> insert(
            @RequestBody @Valid CategoryRequestDTO dto,
            UriComponentsBuilder builder
    ) {
        Category category = categoryService.create(dto);

        URI uri = builder.path("/category/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = categoryService.findAll();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> findById(@PathVariable UUID id) {
        Category category = categoryService.findById(id);

        return ResponseEntity.ok(category);
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> update(@PathVariable UUID id, @RequestBody @Valid CategoryRequestDTO dto) {
        Category category = categoryService.update(id, dto);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
