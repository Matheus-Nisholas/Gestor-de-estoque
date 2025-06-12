package br.com.gestor.gestor_de_estoque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;

    // --- MÉTODOS OBRIGATÓRIOS DA INTERFACE UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por enquanto, vamos dar uma permissão ("ROLE") padrão para todos os usuários.
        // No futuro, poderíamos ter uma tabela de roles no banco e buscar as permissões de lá.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha; // Retorna a senha do nosso usuário
    }

    @Override
    public String getUsername() {
        return this.login; // Retorna o login do nosso usuário
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Para simplificar, consideramos que a conta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Para simplificar, a conta nunca está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Para simplificar, as credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return true; // Para simplificar, o usuário está sempre habilitado
    }
}