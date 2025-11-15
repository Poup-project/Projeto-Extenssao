package com.projetoextensao.Projeto_Extenssao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projetoextensao.Projeto_Extenssao.dto.ClientRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Client")
public class Client {

    public Client(ClientRequestDTO clientRequestDTO) {
        this.name = clientRequestDTO.getName();
        this.email = clientRequestDTO.getEmail();
        this.password = clientRequestDTO.getPassword();
    }


    public Client(UUID id, ClientRequestDTO clientRequestDTO) {
        this.id = id;
        this.name = clientRequestDTO.getName();
        this.email = clientRequestDTO.getEmail();
        this.password = clientRequestDTO.getPassword();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "client-category")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "client-transaction")
    private List<Transaction> transactions = new ArrayList<>();
}
