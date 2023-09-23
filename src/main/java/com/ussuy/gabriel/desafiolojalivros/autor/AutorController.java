package com.ussuy.gabriel.desafiolojalivros.autor;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private NovoAutorValidator novoAutorValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(novoAutorValidator);
    }

    @PostMapping
    @Transactional
    public NovoAutorResponse novoAutor(@Valid @RequestBody NovoAutorRequest novoAutorRequest) {
        Autor novoAutor = novoAutorRequest.toAutor();
        return NovoAutorResponse.from(autorRepository.save(novoAutor));
    }
}
