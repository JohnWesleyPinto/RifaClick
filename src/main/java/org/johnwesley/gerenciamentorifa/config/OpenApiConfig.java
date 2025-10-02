package org.johnwesley.gerenciamentorifa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Gestão de Rifas",
                version = "1.0",
                description = "Documentação da API do sistema de gestão de rifas",
                contact = @Contact(name = "John Wesley", email = "john.silva@dcx.ufpb.br")
        )
)
public class OpenApiConfig {

}
