package io.github.felipe11dias.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.felipe11dias.domain.entity.ItemPedido;
import io.github.felipe11dias.domain.entity.Pedido;
import io.github.felipe11dias.dto.AtualizacaoStatusPedidoDTO;
import io.github.felipe11dias.dto.InformacaoItemPedidoDTO;
import io.github.felipe11dias.dto.InformacoesPedidoDTO;
import io.github.felipe11dias.dto.PedidoDTO;
import io.github.felipe11dias.enums.StatusPedido;
import io.github.felipe11dias.service.PedidoService;

import static org.springframework.http.HttpStatus.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	private PedidoService service;
	
	public PedidoController(PedidoService service) {
		this.service = service;
	}
	
	@PostMapping
	@ResponseStatus(CREATED)
	public Integer save(@RequestBody @Valid PedidoDTO dto) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}
	
	@GetMapping("{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return service
				.obterPedidoCompleto(id)
				.map( p -> converter(p))
				.orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(NO_CONTENT)
	public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto) {
		String novoStatus = dto.getNovoStatus();
		service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
	}

	private InformacoesPedidoDTO converter(Pedido pedido) {
		return InformacoesPedidoDTO
				.builder()
				.codigo(pedido.getId())
				.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.cpf(pedido.getCliente().getCpf())
				.nomeCliente(pedido.getCliente().getNome())
				.total(pedido.getTotal())
				.status(pedido.getStatus().name())
				.items(converter(pedido.getItens()))
				.build();
	}

	private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
		if(itens.isEmpty()) {
			return Collections.emptyList();
		}
		
		return itens.stream().map(
				item -> InformacaoItemPedidoDTO
							.builder()
							.descricaoProduto(item.getProduto().getDescricao())
							.precoUnitario(item.getProduto().getPreco())
							.quantidade(item.getQuantidade())
							.build()
		).collect(Collectors.toList());
	}
	
}
