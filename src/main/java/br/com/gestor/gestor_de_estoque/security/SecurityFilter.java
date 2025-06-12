package br.com.gestor.gestor_de_estoque.security;

import br.com.gestor.gestor_de_estoque.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Identifica como um componente genérico do Spring
public class SecurityFilter extends OncePerRequestFilter { // Garante que o filtro é executado apenas uma vez por requisição

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request); // 1. Recupera o token do cabeçalho da requisição
        if(token != null) {
            var login = tokenService.validarToken(token); // 2. Valida o token
            UserDetails user = usuarioRepository.findByLogin(login); // 3. Busca o usuário no banco

            // 4. Cria o objeto de autenticação para o Spring
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            // 5. Salva o objeto de autenticação no contexto de segurança do Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response); // 6. Continua a execução normal da requisição
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        // O token JWT vem depois do prefixo "Bearer ", então removemos esse texto
        return authHeader.replace("Bearer ", "");
    }
}