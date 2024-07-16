package br.com.forum.desafio.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosUsuarioDto(
    @NotBlank(message = "Campo usuario não pode ser nulo!")
    @Size(min = 3, message = "Senha não pode ser menor que 3 caracteres")
    String usuario,

    @NotBlank(message = "Campo senha não pode ser nulo!")
    @Size(min = 6, message = "Senha não pode ser menor que 6 caracteres")
    String senha
) {
}
