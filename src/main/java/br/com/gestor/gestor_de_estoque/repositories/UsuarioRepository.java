package br.com.gestor.gestor_de_estoque.repositories;

import br.com.gestor.gestor_de_estoque.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo seu campo "login"
    UserDetails findByLogin(String login);
}