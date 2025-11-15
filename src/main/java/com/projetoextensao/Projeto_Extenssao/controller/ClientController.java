package com.projetoextensao.Projeto_Extenssao.controller;

import com.projetoextensao.Projeto_Extenssao.domain.Client;
import com.projetoextensao.Projeto_Extenssao.dto.*;
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

    // ROTA DE LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        Client client = clientService.login(loginRequest.getEmail(), loginRequest.getPassword());
        LoginResponseDTO response = new LoginResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> insert(
            @RequestBody @Valid ClientRequestDTO clientRequestDTO,
            UriComponentsBuilder builder) {
        Client client = new Client(clientRequestDTO);
        client = clientService.create(client);

        ClientResponseDTO response = new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail()
        );

        URI uri = builder.path("/client/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        List<ClientResponseDTO> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable UUID id) {
        Client client = clientService.findById(id);
        ClientResponseDTO response = new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid ClientRequestDTO clientRequestDTO) {
        Client client = new Client(id, clientRequestDTO);
        client = clientService.update(client);

        ClientResponseDTO response = new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
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
