package com.projetoextensao.Projeto_Extenssao.dto;

import com.projetoextensao.Projeto_Extenssao.domain.Transaction;
import lombok.Data;

import java.util.UUID;

@Data
public class TransactionResponseDTO {

    private UUID id;
    private String title;
    private double price;
    private CategoryBasicDTO category;

    public TransactionResponseDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.title = transaction.getTitle();
        this.price = transaction.getPrice();

        if (transaction.getCategory() != null) {
            this.category = new CategoryBasicDTO(transaction.getCategory());
        }
    }
}
