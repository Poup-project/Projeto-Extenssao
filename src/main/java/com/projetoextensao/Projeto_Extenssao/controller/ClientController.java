package com.projetoextensao.Projeto_Extenssao.controller;

import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.dto.ClientRequestDTO;
import com.projetoextensao.Projeto_Extenssao.dto.ClientResponseDTO;
import com.projetoextensao.Projeto_Extenssao.jwt.JwtFilter;
import com.projetoextensao.Projeto_Extenssao.jwt.JwtUtil;
import com.projetoextensao.Projeto_Extenssao.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> insert(@RequestBody @Valid ClientRequestDTO dto,
                                                    UriComponentsBuilder builder) {
        Client client = clientService.create(new Client(dto));
        URI uri = builder.path("/client/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClientResponseDTO(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        List<ClientResponseDTO> clientes = clientService.findAll()
                .stream()
                .map(ClientResponseDTO::new)
                .toList();

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable UUID id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok(new ClientResponseDTO(client));
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("password");

        if (email == null || senha == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email e password são obrigatórios");
        }

        Client client = clientService.findByEmail(email);

        if (client == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha inválidos");
        }

        if (client.getPassword() == null || !client.getPassword().equals(senha)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha inválidos");
        }

        String token = jwtUtil.generateToken(client.getId());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "client", new ClientResponseDTO(client)
        ));
    }

    @GetMapping("/teste")
    public ResponseEntity<?> getCurrentClient() {
        UUID clientId = JwtFilter.getCurrentClientId();
        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente");
        }
        return ResponseEntity.ok("Client ID atual: " + clientId);
    }
}
