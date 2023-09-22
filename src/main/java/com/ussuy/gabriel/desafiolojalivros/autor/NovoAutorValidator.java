package com.ussuy.gabriel.desafiolojalivros.autor;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NovoAutorValidator implements Validator {

    private final AutorRepository autorRepository;

    public NovoAutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NovoAutorRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) {
            return;
        }

        NovoAutorRequest novoAutorRequest = (NovoAutorRequest) target;
        autorRepository.findByEmail(novoAutorRequest.getEmail())
                .ifPresent(autor ->
                        errors.rejectValue("email", null,
                                "Email já está cadastrado " + novoAutorRequest.getEmail()));
    }
}
