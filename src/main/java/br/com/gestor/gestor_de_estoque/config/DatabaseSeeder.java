package br.com.gestor.gestor_de_estoque.config;

import br.com.gestor.gestor_de_estoque.entities.Produto;
import br.com.gestor.gestor_de_estoque.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (produtoRepository.count() == 0) {
            System.out.println("Populando o banco de dados com dados de exemplo...");

            Produto p1 = new Produto(null, "Teclado Mecânico RGB", 50, 349.90, "Periféricos", null);
            Produto p2 = new Produto(null, "Mouse Gamer Sem Fio", 75, 280.00, "Periféricos", null);
            Produto p3 = new Produto(null, "Monitor Ultrawide 29\"", 25, 1450.00, "Monitores", null);
            Produto p4 = new Produto(null, "Monitor Gamer 24\" 144Hz", 30, 980.00, "Monitores", null);
            Produto p5 = new Produto(null, "SSD NVMe 1TB", 40, 599.99, "Armazenamento", null);
            Produto p6 = new Produto(null, "HD Externo Portátil 2TB", 55, 450.00, "Armazenamento", null);
            Produto p7 = new Produto(null, "Memória RAM DDR4 16GB", 60, 320.50, "Componentes", null);
            Produto p8 = new Produto(null, "Processador Core i7", 15, 1800.00, "Componentes", null);

            List<Produto> produtos = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8);

            produtoRepository.saveAll(produtos);

            System.out.println("Dados de exemplo inseridos com sucesso!");
        } else {
            System.out.println("O banco de dados já contém dados. Nenhuma ação foi tomada.");
        }
    }
}