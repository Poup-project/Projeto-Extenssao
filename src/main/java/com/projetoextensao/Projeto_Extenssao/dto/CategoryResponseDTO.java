package com.projetoextensao.Projeto_Extenssao.dto;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class CategoryResponseDTO {

    private UUID id;
    private String title;
    private String colorHex;
    private List<TransactionResponseDTO> transactions;

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.colorHex = category.getColorHex();

        if (category.getTransactions() != null) {
            this.transactions = category.getTransactions().stream()
                    .map(TransactionResponseDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
