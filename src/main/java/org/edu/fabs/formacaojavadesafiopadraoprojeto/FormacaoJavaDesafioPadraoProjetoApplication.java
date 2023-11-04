package org.edu.fabs.formacaojavadesafiopadraoprojeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FormacaoJavaDesafioPadraoProjetoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormacaoJavaDesafioPadraoProjetoApplication.class, args);
	}

}
