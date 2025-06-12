package br.com.gestor.gestor_de_estoque.exceptions;

// Esta é uma exceção "não verificada" (unchecked), ideal para erros de runtime
public class ResourceNotFoundException extends RuntimeException {

    // Construtor que aceita uma mensagem de erro
    public ResourceNotFoundException(String message) {
        super(message); // Passa a mensagem para a classe pai (RuntimeException)
    }
}