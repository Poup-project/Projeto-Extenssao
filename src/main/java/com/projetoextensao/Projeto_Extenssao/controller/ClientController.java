package com.projetoextensao.Projeto_Extenssao.controller;

import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.dto.ClientRequestDTO;
import com.projetoextensao.Projeto_Extenssao.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> insert(@RequestBody @Valid ClientRequestDTO clientRequestDTO,
                                         UriComponentsBuilder builder) {
        Client cliente = new Client(clientRequestDTO);
        cliente = clientService.create(cliente);

        URI uri = builder.path("/clients/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        List<Client> clientes = clientService.findAll();

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("{id}")
    public ResponseEntity<Client> findById(@PathVariable UUID id) {
        Client cliente = clientService.findById(id);

        return ResponseEntity.ok(cliente);
    }

    @PutMapping("{id}")
    public ResponseEntity<Client> update(@PathVariable UUID id,
                                          @RequestBody @Valid ClientRequestDTO clientRequestDTO) {
        Client cliente = new Client(id, clientRequestDTO);
        cliente = clientService.update(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
