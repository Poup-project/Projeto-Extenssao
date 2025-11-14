package com.projetoextensao.Projeto_Extenssao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O valor da transação é obrigatório!")
    private float price;

    @NotNull(message = "A categoria é obrigatória!")
    private UUID categoryId;
}
