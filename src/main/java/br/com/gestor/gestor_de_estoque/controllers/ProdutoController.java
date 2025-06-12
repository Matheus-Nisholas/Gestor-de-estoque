package br.com.gestor.gestor_de_estoque.controllers;

import br.com.gestor.gestor_de_estoque.dto.ProdutoDTO;
import br.com.gestor.gestor_de_estoque.services.ProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@SecurityRequirement(name = "bearerAuth") // Anotação para o Swagger saber que precisa de autenticação
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<ProdutoDTO> listarTodos(
            @RequestParam(name = "categoria", required = false) String categoria,
            @RequestParam(name = "nome", required = false) String nome) {

        if (nome != null) {
            return produtoService.buscarPorNome(nome);
        }
        if (categoria != null) {
            return produtoService.buscarPorCategoria(categoria);
        }
        return produtoService.listarTodosProdutos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO produtoDTO = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produtoDTO);
    }

    @PostMapping
    public ProdutoDTO criarProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
        return produtoService.salvarProduto(produtoDTO);
    }

    @PutMapping("/{id}")
    public ProdutoDTO atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDetalhes) {
        return produtoService.atualizarProduto(id, produtoDetalhes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}