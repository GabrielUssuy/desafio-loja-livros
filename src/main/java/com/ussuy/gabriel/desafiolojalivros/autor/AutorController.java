package com.ussuy.gabriel.desafiolojalivros.autor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public NovoAutorResponse novoAutor(@Valid @RequestBody NovoAutorRequest novoAutorRequest) {
        Autor novoAutor = novoAutorRequest.toAutor();
        entityManager.persist(novoAutor);
        return NovoAutorResponse.from(novoAutor);
    }
}
