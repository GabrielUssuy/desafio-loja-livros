package com.ussuy.gabriel.desafiolojalivros.config;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    private final MessageSource messageSource;

    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<String> handle(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors()
                .stream().map(this::recuperarMensagemDeErro).collect(Collectors.toList());
    }

    private String recuperarMensagemDeErro(ObjectError objectError) {
        for(String code : objectError.getCodes()) {
            try {
                return messageSource.getMessage(code, null, Locale.getDefault());
            } catch (Exception e) {
                continue;
            }
        }

        return objectError.getDefaultMessage();
    }
}
