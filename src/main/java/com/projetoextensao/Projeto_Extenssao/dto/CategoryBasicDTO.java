package com.projetoextensao.Projeto_Extenssao.dto;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryBasicDTO {

    private UUID id;
    private String title;
    private String colorHex;

    public CategoryBasicDTO(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.colorHex = category.getColorHex();
    }
}
