package com.ussuy.gabriel.desafiolojalivros.autor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NovoAutorRequest {

    private static final int TAMANHO_LIMITE_DESCRICAO = 400;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @Email(message = "Email informado não é válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;
    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = TAMANHO_LIMITE_DESCRICAO, message = "Descrição ultrapassou o tamanho máximo de 400 caracteres")
    private String descricao;

    public NovoAutorRequest(@NotBlank  String nome,
                            @NotBlank @Email String email,
                            @NotBlank @Size(max = TAMANHO_LIMITE_DESCRICAO) String descricao) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }

    public Autor toAutor() {
        return new Autor(nome, email, descricao);
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDescricao() {
        return descricao;
    }
}
