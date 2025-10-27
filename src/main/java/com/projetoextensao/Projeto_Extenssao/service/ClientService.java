package com.projetoextensao.Projeto_Extenssao.service;

import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client findById(UUID id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));
    }

    public Client create(Client cliente) {
        return clientRepository.save(cliente);
    }

    public Client update(Client cliente) {
        return clientRepository.save(cliente);
    }

    public void delete(UUID id) {
        Client cliente = findById(id);
        clientRepository.delete(cliente);
    }
}
