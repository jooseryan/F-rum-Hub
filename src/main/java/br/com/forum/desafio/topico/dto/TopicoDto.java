package br.com.forum.desafio.topico.dto;

import br.com.forum.desafio.topico.entities.Topico;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TopicoDto(@NotNull
                        @Size(max = 255, message = "Tamanho do título não pode ser maior que 255 caracteres!")
                        String titulo,

                        @NotNull
                        String mensagem,

                        @NotNull
                        @PastOrPresent(message = "Data deve ser anterior ao momento atual")
                        LocalDateTime dataCriacao,

                        @NotNull
                        boolean topicoAtivo,

                        @NotNull
                        String autor,

                        @NotNull
                        String curso) {
    public TopicoDto(Topico topico) {
        this(topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getTopicoAtivo(), topico.getAutor(),
                topico.getCurso());
    }
}
