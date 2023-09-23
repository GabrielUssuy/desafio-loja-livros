package com.ussuy.gabriel.desafiolojalivros.autor;

import com.ussuy.gabriel.desafiolojalivros.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(AutorController.class)
class AutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AutorRepository autorRepository;
    @MockBean
    private NovoAutorValidator novoAutorValidator;

    @BeforeEach
    public void beforeEach() {
        when(novoAutorValidator.supports(any())).thenReturn(true);
    }

    private static final String URL_NOVO_AUTOR = "/autores";
    private static final String NOME_AUTOR = "Antonio Nunes";
    private static final String EMAIL_AUTOR = "antonio.nunes@gmail.com";
    private static final String DESCRICAO_AUTOR = "Entusiasta de comédia";

    @Test
    public void deve_cadastrar_autor_quando_request_atende_requisitos() throws Exception {
        String requestBody = JsonUtils.toJsonString(new NovoAutorRequest(NOME_AUTOR, EMAIL_AUTOR, DESCRICAO_AUTOR));
        String responseBody = JsonUtils.toJsonString(new NovoAutorResponse(1L, NOME_AUTOR, EMAIL_AUTOR, DESCRICAO_AUTOR));

        Long idAutorCadastrado = 1L;
        Autor autor = new Autor(idAutorCadastrado,NOME_AUTOR, EMAIL_AUTOR, DESCRICAO_AUTOR);
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        validarPost(URL_NOVO_AUTOR, requestBody, HttpStatus.OK, responseBody);

        verify(novoAutorValidator).validate(any(), any(Errors.class));
    }

    @Test
    public void deve_retornar_erro_quando_nome_estiver_em_branco() throws Exception {
        String requestBody = JsonUtils.toJsonString(
                new NovoAutorRequest("", EMAIL_AUTOR, DESCRICAO_AUTOR));
        String responseBody = "[\"Nome é obrigatório\"]";
        validarPost(URL_NOVO_AUTOR, requestBody, HttpStatus.BAD_REQUEST, responseBody);
    }

    @Test
    public void deve_retornar_erro_quando_nome_for_nulo() throws Exception {
        String requestBody = JsonUtils.toJsonString(
                new NovoAutorRequest(null, EMAIL_AUTOR, DESCRICAO_AUTOR));
        String responseBody = "[\"Nome é obrigatório\"]";
        validarPost(URL_NOVO_AUTOR, requestBody, HttpStatus.BAD_REQUEST, responseBody);
    }

    @Test
    public void deve_retornar_erro_quando_email_estiver_em_branco() throws Exception {
        String requestBody = JsonUtils.toJsonString(
                new NovoAutorRequest(NOME_AUTOR, "", DESCRICAO_AUTOR));
        String responseBody = "[\"Email é obrigatório\"]";
        validarPost(URL_NOVO_AUTOR, requestBody, HttpStatus.BAD_REQUEST, responseBody);
    }

    @Test
    public void deve_retornar_erro_quando_email_for_nulo() throws Exception {
        String requestBody = JsonUtils.toJsonString(
                new NovoAutorRequest(NOME_AUTOR, null, DESCRICAO_AUTOR));
        String responseBody = "[\"Email é obrigatório\"]";
        validarPost(URL_NOVO_AUTOR, requestBody, HttpStatus.BAD_REQUEST, responseBody);
    }

    @Test
    public void deve_retornar_erro_quando_email_for_invalido() throws Exception {
        String requestBody = JsonUtils.toJsonString(
                new NovoAutorRequest(NOME_AUTOR, "email.invalido", DESCRICAO_AUTOR));
        String responseBody = "[\"Email informado não é válido\"]";
        validarPost(URL_NOVO_AUTOR, requestBody, HttpStatus.BAD_REQUEST, responseBody);
    }

    @Test
    public void deve_retornar_erro_quando_descricao_estiver_em_branco() throws Exception {
        String requestBody = JsonUtils.toJsonString(
                new NovoAutorRequest(NOME_AUTOR, EMAIL_AUTOR, ""));
        String responseBody = "[\"Descrição é obrigatória\"]";
        validarPost(URL_NOVO_AUTOR, requestBody, HttpStatus.BAD_REQUEST, responseBody);
    }

    @Test
    public void deve_retornar_erro_quando_descricao_for_nulo() throws Exception {
        String requestBody = JsonUtils.toJsonString(
                new NovoAutorRequest(NOME_AUTOR, EMAIL_AUTOR, null));
        String responseBody = "[\"Descrição é obrigatória\"]";
        validarPost(URL_NOVO_AUTOR, requestBody, HttpStatus.BAD_REQUEST, responseBody);
    }

    @Test
    public void deve_retornar_erro_quando_descricao_ultrapassar_limite_caracteres() throws Exception {
        String descricaoInvalida = "Lorem ipsum dolor sit amet. Ut dolor perspiciatis et iusto internos qui similique quasi " +
                "ab quidem odit aut dolor recusandae qui dolores veritatis. Est provident mollitia aut consequatur " +
                "adipisci et nesciunt culpa ut tenetur aperiam et eaque quia. Quo facilis amet At tempora voluptas ut " +
                "minus similique sit ducimus deleniti cum illo dicta eum consectetur ipsam a unde dolores? Sit illo " +
                "pariatur et sint dolorum et recusandae voluptatem eum ipsa dolorum et quod nisi et corporis nihil.";

        String requestBody = JsonUtils.toJsonString(
                new NovoAutorRequest(NOME_AUTOR, EMAIL_AUTOR, descricaoInvalida));
        String responseBody = "[\"Descrição ultrapassou o tamanho máximo de 400 caracteres\"]";

        validarPost(URL_NOVO_AUTOR, requestBody, HttpStatus.BAD_REQUEST, responseBody);
    }

    private void validarPost(String url, String requestBody, HttpStatus responseStatus, String responseBody) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(url).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(responseStatus.value()))
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

}