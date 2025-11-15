package com.projetoextensao.Projeto_Extenssao.repository;

import com.projetoextensao.Projeto_Extenssao.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);
}
