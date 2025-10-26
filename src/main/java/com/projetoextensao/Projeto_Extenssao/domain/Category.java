package com.projetoextensao.Projeto_Extenssao.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projetoextensao.Projeto_Extenssao.dto.CategoryRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "Category")
public class Category {

    public Category(CategoryRequestDTO categoryRequestDTO) {
        this.title = categoryRequestDTO.getTitle();
        this.colorHex = categoryRequestDTO.getColorHex();
    }

    public Category(UUID id, CategoryRequestDTO categoryRequestDTO) {
        this.id = id;
        this.title = categoryRequestDTO.getTitle();
        this.colorHex = categoryRequestDTO.getColorHex();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(name = "color_hex")
    private String colorHex;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Client client;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();
}
