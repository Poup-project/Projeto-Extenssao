package com.projetoextensao.Projeto_Extenssao.repository;

import com.projetoextensao.Projeto_Extenssao.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
