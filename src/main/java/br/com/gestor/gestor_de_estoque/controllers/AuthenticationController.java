package br.com.gestor.gestor_de_estoque.controllers;

import br.com.gestor.gestor_de_estoque.dto.AuthenticationDTO;
import br.com.gestor.gestor_de_estoque.dto.LoginResponseDTO;
import br.com.gestor.gestor_de_estoque.dto.RegisterDTO;
import br.com.gestor.gestor_de_estoque.entities.Usuario;
import br.com.gestor.gestor_de_estoque.repositories.UsuarioRepository;
import br.com.gestor.gestor_de_estoque.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    // NOSSO NOVO MÉTODO DE REGISTRO
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        // 1. Verifica se o login já existe no banco
        if (this.usuarioRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body("Erro: Login já está em uso!");
        }

        // 2. Codifica a senha antes de salvar (Passo de segurança CRUCIAL)
        String encryptedPassword = passwordEncoder.encode(data.senha());

        // 3. Cria um novo objeto Usuario com os dados recebidos e a senha codificada
        Usuario newUser = new Usuario(null, data.login(), encryptedPassword);

        // 4. Salva o novo usuário no banco de dados
        this.usuarioRepository.save(newUser);

        // 5. Retorna uma resposta de sucesso (200 OK)
        return ResponseEntity.ok().build();
    }
}