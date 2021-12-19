package io.github.felipe11dias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TreinamentoVendasApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TreinamentoVendasApplication.class, args);
	}

}
