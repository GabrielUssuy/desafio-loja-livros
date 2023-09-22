package com.ussuy.gabriel.desafiolojalivros.autor;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autor")
public class AutorController {

    private final AutorRepository autorRepository;
    private final NovoAutorValidator novoAutorValidator;

    public AutorController(AutorRepository autorRepository, NovoAutorValidator novoAutorValidator) {
        this.autorRepository = autorRepository;
        this.novoAutorValidator = novoAutorValidator;
    }

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(novoAutorValidator);
    }

    @PostMapping
    @Transactional
    public NovoAutorResponse novoAutor(@RequestBody @Valid NovoAutorRequest novoAutorRequest) {
        Autor novoAutor = novoAutorRequest.toAutor();
        autorRepository.save(novoAutor);
        return NovoAutorResponse.from(novoAutor);
    }
}
