package io.github.felipe11dias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.felipe11dias.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer> {

}
