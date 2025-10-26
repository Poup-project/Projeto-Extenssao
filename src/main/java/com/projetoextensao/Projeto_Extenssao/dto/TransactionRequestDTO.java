package com.projetoextensao.Projeto_Extenssao.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotBlank(message = "O título da transação é obrgatório!")
    private String title;

    @NotBlank(message = "O valor da transação é obrigatório!")
    private float price;

    @NotBlank(message = "A categoria é obrigatória!")
    private UUID categoryId;

    @NotBlank(message = "O cliente é obrigatório!")
    private UUID userId;
}
