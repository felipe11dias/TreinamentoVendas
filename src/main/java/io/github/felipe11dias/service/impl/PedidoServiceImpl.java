package io.github.felipe11dias.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import io.github.felipe11dias.domain.entity.Cliente;
import io.github.felipe11dias.domain.entity.ItemPedido;
import io.github.felipe11dias.domain.entity.Pedido;
import io.github.felipe11dias.domain.entity.Produto;
import io.github.felipe11dias.dto.ItemPedidoDTO;
import io.github.felipe11dias.dto.PedidoDTO;
import io.github.felipe11dias.enums.StatusPedido;
import io.github.felipe11dias.exception.PedidoNaoEncontradoException;
import io.github.felipe11dias.exception.RegraNegocioException;
import io.github.felipe11dias.repository.Clientes;
import io.github.felipe11dias.repository.ItemsPedidos;
import io.github.felipe11dias.repository.Pedidos;
import io.github.felipe11dias.repository.Produtos;
import io.github.felipe11dias.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
	
	private final Pedidos pedidosRepository;
	private final Clientes clientesReposistory;
	private final Produtos produtosReposistory;
	private final ItemsPedidos itemsPedidoReposistory;
	
	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Integer idCliente = dto.getCliente();
		Cliente cliente = clientesReposistory
							.findById(idCliente)
							.orElseThrow( () -> new RegraNegocioException("Código de cliente inválido."));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
		pedidosRepository.save(pedido);
		itemsPedidoReposistory.saveAll(itemsPedido);
		pedido.setItens(itemsPedido);
		return pedido;
	}
	
	
	private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
		if(items.isEmpty()) {
			throw new RegraNegocioException("Não é possivel realizar um pedido sem items.");
		}
		
		return items
				.stream()
				.map( dto -> {
					Integer idProduto = dto.getProduto();
					Produto produto = produtosReposistory
										.findById(idProduto)
										.orElseThrow( () -> new RegraNegocioException("Código de produto inválido: " + idProduto));
						
					
					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setQuantidade(dto.getQuantidade());
					itemPedido.setPedido(pedido);
					itemPedido.setProduto(produto);
					return itemPedido;
				}).collect(Collectors.toList());
	}


	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return pedidosRepository.findByIdFetchItens(id);
	}


	@Override
	public void atualizaStatus(Integer id, StatusPedido statusPedido) {
		pedidosRepository
			.findById(id)
			.map( pedido -> {
				pedido.setStatus(statusPedido);
				return pedidosRepository.save(pedido);
			})
			.orElseThrow( () -> new PedidoNaoEncontradoException());
	}
	
}
