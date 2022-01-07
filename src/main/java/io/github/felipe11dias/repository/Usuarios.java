package io.github.felipe11dias.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.felipe11dias.domain.entity.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Integer> {

	
	Optional<Usuario> findByLogin(String login);
}
