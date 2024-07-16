package br.com.forum.desafio.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErroApiDto(LocalDateTime timestamp,
                         Integer status_code,
                         String error_name,
                         String message,
                         String path
) {

}
