package br.com.forum.desafio.topico.validacoes;

import br.com.forum.desafio.topico.dto.CriarTopicoDto;
import br.com.forum.desafio.topico.entities.Topico;
import br.com.forum.desafio.topico.exceptions.ValidacaoException;
import br.com.forum.desafio.topico.repositories.TopicoRepository;
import br.com.forum.desafio.util.Validacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidarTopicoDuplicado implements Validacao<CriarTopicoDto> {
    @Autowired
    private TopicoRepository repository;

    @Override
    public void validar(CriarTopicoDto dto) {
        Optional<Topico> topicoEncontrado = repository.buscarTopicoPorTituloEMensagem(dto.titulo(), dto.mensagem());

        if(topicoEncontrado.isPresent())
            throw new ValidacaoException("Tópico duplicado! Já existe um com o mesmo título e a mesma mensagem!");
    }
}
