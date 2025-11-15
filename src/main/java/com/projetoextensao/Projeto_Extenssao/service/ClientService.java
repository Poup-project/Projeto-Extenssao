package com.projetoextensao.Projeto_Extenssao.service;

import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.dto.ClientResponseDTO;
import com.projetoextensao.Projeto_Extenssao.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // MÉTODO NOVO: Login do usuário
    public Client login(String email, String password) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos!"));

        if (!client.getPassword().equals(password)) {
            throw new RuntimeException("Email ou senha inválidos!");
        }

        return client;
    }

    // MÉTODO ATUALIZADO: Create com validação de email único
    public Client create(Client cliente) {
        // Verificar se email já existe
        Optional<Client> existingClient = clientRepository.findByEmail(cliente.getEmail());
        if (existingClient.isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }
        return clientRepository.save(cliente);
    }

    // MÉTODO ATUALIZADO: FindAll retornando DTOs (para o Controller)
    public List<ClientResponseDTO> findAll(){
        return clientRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // MÉTODO MANTIDO: FindAll retornando entidades (para uso interno se necessário)
    public List<Client> findAllEntities(){
        return clientRepository.findAll();
    }

    // MÉTODO ATUALIZADO: FindById retornando entidade (para uso interno)
    public Client findById(UUID id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));
    }

    // MÉTODO ATUALIZADO: Update com validação de email único
    public Client update(Client cliente) {
        Client existingClient = findById(cliente.getId());

        // Verificar se outro cliente já tem o email (se mudou o email)
        if (!existingClient.getEmail().equals(cliente.getEmail())) {
            Optional<Client> clientWithEmail = clientRepository.findByEmail(cliente.getEmail());
            if (clientWithEmail.isPresent()) {
                throw new RuntimeException("Email já está em uso por outro cliente!");
            }
        }

        existingClient.setName(cliente.getName());
        existingClient.setEmail(cliente.getEmail());
        existingClient.setPassword(cliente.getPassword());

        return clientRepository.save(existingClient);
    }

    public void delete(UUID id) {
        Client cliente = findById(id);
        clientRepository.delete(cliente);
    }

    // MÉTODO NOVO: Converter para DTO de response
    public ClientResponseDTO toResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail()
        );
    }
}
