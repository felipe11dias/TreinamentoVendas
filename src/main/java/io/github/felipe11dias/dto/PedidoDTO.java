package io.github.felipe11dias.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.github.felipe11dias.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {
	
	@NotNull(message = "{campo.codigo-cliente.obrigatorio}")
	public Integer cliente;
	@NotNull(message = "{campo.total-pedido.obrigatorio}")
	public BigDecimal total;
	@NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
	public List<ItemPedidoDTO> items;
	
}
