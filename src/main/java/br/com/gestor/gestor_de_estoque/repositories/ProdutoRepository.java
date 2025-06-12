package br.com.gestor.gestor_de_estoque.repositories;

import java.util.List;
import br.com.gestor.gestor_de_estoque.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, mas boa prática para identificar o bean no contexto do Spring
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria(String categoria);
    List<Produto> findByNomeContainingIgnoreCase(String nome);

}
