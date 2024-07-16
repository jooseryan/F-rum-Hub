package br.com.forum.desafio.exception;

import br.com.forum.desafio.topico.exceptions.ValidacaoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratamentoDeErros {
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ErroApiDto> tratarErro409ConflictValidacaoException
            (ValidacaoException ex, HttpServletRequest req){

        var codigoStatus = HttpStatus.CONFLICT;
        var dtoErro = construirDtoErro(ex, req, codigoStatus);

        return new ResponseEntity<ErroApiDto>(dtoErro, codigoStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroApiDtoMultiplosErros> tratarErro400MethodArgumentNotValidException
            (MethodArgumentNotValidException ex, HttpServletRequest req) {

        var erros = ex.getFieldErrors().stream().map(ErroCampo::new).toList();
        var dtoErro = construirDtoMultiplosErros(ex, req, erros, HttpStatus.valueOf(ex.getStatusCode().value()));

        return ResponseEntity.badRequest().body(dtoErro);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErroApiDtoMultiplosErros> tratarErro400HandlerMethodValidationException
            (HandlerMethodValidationException ex, HttpServletRequest req) {

        var erros = ex.getAllErrors().stream()
                .map(e -> (FieldError) e)
                .map(ErroCampo::new)
                .toList();

        var dtoErro = construirDtoMultiplosErros(ex, req, erros, HttpStatus.valueOf(ex.getStatusCode().value()));

        return ResponseEntity.badRequest().body(dtoErro);
    }

    @ExceptionHandler(TopicoNaoEncontradoException.class)
    public ResponseEntity<ErroApiDto> tratarErro404TopicoNaoEncontradoException
            (TopicoNaoEncontradoException ex, HttpServletRequest req){

        var dtoErro = construirDtoErro(ex, req, HttpStatus.NOT_FOUND);

        return new ResponseEntity<ErroApiDto>(dtoErro, HttpStatus.valueOf(dtoErro.status_code()));
    }

    private ErroApiDto construirDtoErro(Exception ex, HttpServletRequest req, HttpStatus status){
        return ErroApiDto.builder()
                .timestamp(LocalDateTime.now())
                .status_code(status.value())
                .error_name(status.name())
                .message(ex.getMessage())
                .path(req.getServletPath())
                .build();
    }

    private ErroApiDtoMultiplosErros construirDtoMultiplosErros(Exception ex, HttpServletRequest req,
                                                                List<ErroCampo> erroCampos, HttpStatus status){
        return ErroApiDtoMultiplosErros.builder()
                .timestamp(LocalDateTime.now())
                .status_code(status.value())
                .error_name(status.name())
                .message("Erros nos seguintes campos do body: " + erroCampos.stream().map(ErroCampo::campo)
                        .collect(Collectors.joining(", ")))
                .path(req.getServletPath())
                .erros(erroCampos)
                .build();
    }
}
