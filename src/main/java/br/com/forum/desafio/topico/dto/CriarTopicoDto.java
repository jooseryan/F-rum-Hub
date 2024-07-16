package br.com.forum.desafio.topico.dto;

import jakarta.validation.constraints.NotBlank;

public record CriarTopicoDto(@NotBlank
                              String titulo,

                             @NotBlank
                              String mensagem,

                             @NotBlank
                              String autor,

                             @NotBlank
                              String curso){
}
