package io.github.felipe11dias.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
	
	
	public PedidoNaoEncontradoException() {
		super("Pedido não encontrado.");
	}
}
