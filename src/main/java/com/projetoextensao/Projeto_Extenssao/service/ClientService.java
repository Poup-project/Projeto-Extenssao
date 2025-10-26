package com.projetoextensao.Projeto_Extenssao.service;

import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Client findById(UUID id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));
    }

    @Transactional
    public Client create(Client cliente) {
        return clientRepository.save(cliente);
    }

    @Transactional
    public Client update(Client cliente) {
        // Verifica se o cliente existe antes de atualizar
        if (cliente.getId() != null) {
            findById(cliente.getId()); // Isso garante que o cliente existe
        }
        return clientRepository.save(cliente);
    }

    @Transactional
    public void delete(UUID id) {
        Client cliente = findById(id);
        clientRepository.delete(cliente);
    }
}
