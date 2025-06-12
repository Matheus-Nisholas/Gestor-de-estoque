package br.com.gestor.gestor_de_estoque.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API do Gestor de Estoque",
                version = "v1",
                description = "Uma API para gerenciar um estoque de produtos."
        )
)
@SecurityScheme(
        name = "bearerAuth", // Um nome para o nosso esquema de segurança
        type = SecuritySchemeType.HTTP, // O tipo de segurança é HTTP
        scheme = "bearer", // O esquema específico é 'bearer'
        bearerFormat = "JWT" // O formato do token é JWT
)
public class OpenApiConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}