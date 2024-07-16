package br.com.forum.desafio.usuario.validacoes;

import br.com.forum.desafio.topico.exceptions.ValidacaoException;
import br.com.forum.desafio.usuario.dto.DadosUsuarioDto;
import br.com.forum.desafio.usuario.repository.UsuarioRepository;
import br.com.forum.desafio.util.Validacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarUsuarioJaExistente implements Validacao<DadosUsuarioDto> {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validar(DadosUsuarioDto dto) {
        if(repository.existsByUsuario(dto.usuario()))
            throw new ValidacaoException("Usuário com email informado já existe!");
    }
}
