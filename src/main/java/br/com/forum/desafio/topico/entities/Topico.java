package br.com.forum.desafio.topico.entities;

import br.com.forum.desafio.topico.dto.CriarTopicoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean topicoAtivo;
    private String autor;
    private String curso;

    public Topico(CriarTopicoDto dto) {
        this.titulo = dto.titulo();
        this.mensagem = dto.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.topicoAtivo = true;
        this.autor = dto.autor();
        this.curso = dto.curso();
    }
}
