package com.ussuy.gabriel.desafiolojalivros.autor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.mockito.Mockito.*;

class NovoAutorValidatorTest {

    private AutorRepository autorRepository = mock(AutorRepository.class);

    private NovoAutorValidator novoAutorValidator = new NovoAutorValidator(autorRepository);

    @Test
    public void deve_permitir_email_unico() {
        NovoAutorRequest novoAutorRequest = new NovoAutorRequest("Nome", "email", "Descricao");
        Errors errors = mock(Errors.class);

        when(autorRepository.findByEmail("email")).thenReturn(Optional.empty());

        novoAutorValidator.validate(novoAutorRequest, errors);

        verify(errors, never()).rejectValue(any(), any(), any());
    }

    @Test
    public void deve_proibir_email_duplicado() {
        NovoAutorRequest novoAutorRequest = new NovoAutorRequest("Nome", "email", "Descricao");
        Errors errors = mock(Errors.class);

        when(autorRepository.findByEmail("email")).thenReturn(Optional.of(new Autor()));

        novoAutorValidator.validate(novoAutorRequest, errors);

        verify(errors).rejectValue("email", null,"Email já está cadastrado email" );
    }

    @Test
    public void deve_validar_a_classe_NovoAutorRequest() {
        Assert.assertTrue(novoAutorValidator.supports(NovoAutorRequest.class));
    }

}