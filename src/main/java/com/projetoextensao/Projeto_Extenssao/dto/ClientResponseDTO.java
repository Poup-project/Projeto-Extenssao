package com.projetoextensao.Projeto_Extenssao.dto;

import com.projetoextensao.Projeto_Extenssao.domain.Client;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class ClientResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private List<CategoryResponseDTO> categories;

    public ClientResponseDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();

        if (client.getCategories() != null) {
            this.categories = client.getCategories().stream()
                    .map(CategoryResponseDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
