package com.projetoextensao.Projeto_Extenssao.repository;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByClientId(UUID clientId);
}
