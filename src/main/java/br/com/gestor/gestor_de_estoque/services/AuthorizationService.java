package br.com.gestor.gestor_de_estoque.services;

import br.com.gestor.gestor_de_estoque.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // O Spring Security vai chamar este método quando o endpoint /login for acionado
        // para buscar o usuário no banco de dados pelo seu login.
        return repository.findByLogin(username);
    }
}