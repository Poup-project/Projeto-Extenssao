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
public class CategoryRequestDTO {

    @NotBlank (message = "O título da categoria não pode ser nulo!")
    private String title;

    @NotBlank(message = "Cor é obrigatória!")
    private String colorHex;


}
