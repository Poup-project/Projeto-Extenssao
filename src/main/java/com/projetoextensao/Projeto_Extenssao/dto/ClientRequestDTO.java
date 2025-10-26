package com.projetoextensao.Projeto_Extenssao.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {

    @NotBlank(message = "O nome do cliente não pode ser nulo!")
    @Size(min = 3, max = 60, message = "O nome deve ter entre 3 a 60 caracteres")
    private String name;

    @NotBlank(message = "O email do cliente não pode ser nulo!")
    @Email(message = "Email inválido!")
    private String email;

    @NotBlank(message = "A senha do cliente não pode ser nula!")
    private String password;
}
