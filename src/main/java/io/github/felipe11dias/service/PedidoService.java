package io.github.felipe11dias.service;

import java.util.Optional;

import io.github.felipe11dias.domain.entity.Pedido;
import io.github.felipe11dias.dto.PedidoDTO;
import io.github.felipe11dias.enums.StatusPedido;

public interface PedidoService {
	Pedido salvar( PedidoDTO dto );
	Optional<Pedido> obterPedidoCompleto(Integer id);
	void atualizaStatus(Integer id, StatusPedido statusPedido);
}
