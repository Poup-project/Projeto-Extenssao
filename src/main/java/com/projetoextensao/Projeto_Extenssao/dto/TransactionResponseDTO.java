package com.projetoextensao.Projeto_Extenssao.dto;

import com.projetoextensao.Projeto_Extenssao.domain.Transaction;
import lombok.Data;

import java.util.UUID;

@Data
public class TransactionResponseDTO {

    private UUID id;
    private String title;
    private double price;
    private UUID categoryId;

    public TransactionResponseDTO(Transaction t) {
        this.id = t.getId();
        this.title = t.getTitle();
        this.price = t.getPrice();
        this.categoryId = t.getCategory().getId();
    }
}
