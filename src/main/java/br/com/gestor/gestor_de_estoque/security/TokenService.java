package br.com.gestor.gestor_de_estoque.security;

import br.com.gestor.gestor_de_estoque.entities.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}") // Lê o valor da nossa propriedade
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            // Algoritmo de assinatura com o nosso segredo
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("gestor-de-estoque-api") // Quem emitiu o token
                    .withSubject(usuario.getLogin()) // A quem o token se refere (o usuário)
                    .withExpiresAt(gerarDataDeExpiracao()) // Define o tempo de validade
                    .sign(algoritmo); // Assina o token
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("gestor-de-estoque-api") // Verifica se o emissor é o mesmo que o nosso
                    .build()
                    .verify(token) // Verifica a assinatura e a validade do token. Se for inválido, lança uma exceção.
                    .getSubject(); // Se for válido, retorna o 'subject' (o login do usuário)
        } catch (JWTVerificationException exception) {
            return ""; // Se o token for inválido (expirado, assinatura errada, etc.), retorna uma string vazia
        }
    }

    private Instant gerarDataDeExpiracao() {
        // Define que o token irá expirar em 2 horas, considerando o fuso horário de Brasília (-03:00)
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}