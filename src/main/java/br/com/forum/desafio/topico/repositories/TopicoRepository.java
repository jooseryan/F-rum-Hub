package br.com.forum.desafio.topico.repositories;

import br.com.forum.desafio.topico.dto.TopicoDto;
import br.com.forum.desafio.topico.entities.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    @Query("""
            select t from Topico t
            where t.titulo = :titulo and
            t.mensagem = :mensagem
            """)
    Optional<Topico> buscarTopicoPorTituloEMensagem(String titulo, String mensagem);

    @Query("Select t from Topico t")
    Page<TopicoDto> listarPaginacao(Pageable pageable);

    @Query("Select t from Topico t where t.curso = :curso")
    Page<TopicoDto> listarPorCurso(Pageable pageable, String curso);

    @Query("Select t from Topico t where YEAR(t.dataCriacao) = :ano")
    Page<TopicoDto> listarPorAno(Integer ano, Pageable pageable);
}
