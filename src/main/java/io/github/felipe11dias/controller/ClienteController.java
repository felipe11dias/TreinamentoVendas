package io.github.felipe11dias.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.felipe11dias.domain.entity.Cliente;
import io.github.felipe11dias.repository.Clientes;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	@Autowired
	private Clientes clientesRepository;	

	@GetMapping("/{id}")
	public Cliente getClienteById(@PathVariable Integer id) {
		return clientesRepository
				.findById(id)
				.orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(CREATED)
	public Cliente save(@RequestBody @Valid Cliente cliente) {
		return clientesRepository.save(cliente);
	}
	
	@DeleteMapping("/deletar")
	@ResponseStatus(NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		clientesRepository.findById(id)
						  .map(cliente -> {
							  clientesRepository.delete(cliente);
							  return cliente;
						  })
						  .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
		clientesRepository
			.findById(id)
			.map( clienteExistente -> {
				cliente.setId(clienteExistente.getId());
				clientesRepository.save(cliente);
				return clienteExistente;
			}).orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
	}
	
	@GetMapping
	public List<Cliente> find(Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example<Cliente> example = Example.of(filtro, matcher);
		return clientesRepository.findAll(example);
	}
}
