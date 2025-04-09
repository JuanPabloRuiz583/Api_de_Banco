package br.com.fiap.ApiBank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API de um banco", description = "API de um banco digital, ligada com o banco de dados, que realiza transaçoes de deposito para uma conta, saque, pix e entre outras funcionalidades", version = "v1"))
public class ApiBankApplication {


	public static void main(String[] args) {
		SpringApplication.run(ApiBankApplication.class, args);
	}

}
