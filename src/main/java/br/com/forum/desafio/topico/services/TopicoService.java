package br.com.forum.desafio.topico.services;

import br.com.forum.desafio.exception.TopicoNaoEncontradoException;
import br.com.forum.desafio.topico.dto.CriarTopicoDto;
import br.com.forum.desafio.topico.dto.TopicoDto;
import br.com.forum.desafio.topico.entities.Topico;
import br.com.forum.desafio.topico.repositories.TopicoRepository;
import br.com.forum.desafio.util.Validacao;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<Validacao<CriarTopicoDto>> validacoesCriarTopico;

    public Topico criarTopico(CriarTopicoDto dto){
        Topico topico = new Topico(dto);

        validacoesCriarTopico.forEach(v -> v.validar(dto));

        topicoRepository.save(topico);
        return topico;
    }

    public TopicoDto atualizarTopico(CriarTopicoDto dto, Long id) {

        var topico = topicoRepository.findById(id).orElseThrow(() -> new TopicoNaoEncontradoException(
                "Tópico não encontrado!"
        ));

        validacoesCriarTopico.forEach(v -> v.validar(dto));

        topico.setTitulo(dto.titulo());
        topico.setMensagem(dto.mensagem());
        topico.setAutor(dto.autor());
        topico.setCurso(dto.curso());

        return new TopicoDto(topico);
    }

    public TopicoDto buscarPorId(Long id) {
        var topico = topicoRepository.findById(id).orElseThrow(() -> new TopicoNaoEncontradoException("Topico não encontrado!"));
        return new TopicoDto(topico);
    }

    public Page<TopicoDto> buscarTodos(Pageable pageable) {
        return topicoRepository.listarPaginacao(pageable);
    }

    public Page<TopicoDto> buscarPorCurso(String curso, Pageable pageable) {
        curso = curso.replace("-", " ");
        var topicos = topicoRepository.listarPorCurso(pageable, curso);
        if(topicos.isEmpty())
            throw new TopicoNaoEncontradoException("Não foram encontrados tópicos! Verifique o nome do curso");
        return topicos;
    }

    public Page<TopicoDto> buscarPorAno(Integer ano, Pageable pageable) {
        var topicos = topicoRepository.listarPorAno(ano, pageable);
        if(topicos.isEmpty())
            throw new TopicoNaoEncontradoException("Não foram encontrados tópicos do ano " + ano);
        return topicos;
    }


    public TopicoDto deletarPorId(Long id) {
        var topico = topicoRepository.findById(id).orElseThrow(() -> new TopicoNaoEncontradoException("Topico não encontrado!"));
        topicoRepository.deleteById(id);
        return new TopicoDto(topico);
    }
}
