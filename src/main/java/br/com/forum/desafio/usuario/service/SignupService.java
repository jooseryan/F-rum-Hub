package br.com.forum.desafio.usuario.service;

import br.com.forum.desafio.usuario.dto.DadosUsuarioDto;
import br.com.forum.desafio.usuario.entity.Usuario;
import br.com.forum.desafio.usuario.repository.UsuarioRepository;
import br.com.forum.desafio.util.Validacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class SignupService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private List<Validacao<DadosUsuarioDto>> validacoesCriarUsuario;

    public void criarUsuario(DadosUsuarioDto dto) {
        validacoesCriarUsuario.forEach(v -> v.validar(dto));

        Usuario usuario = new Usuario(new DadosUsuarioDto(dto.usuario(), passwordEncoder.encode(dto.senha())));

        repository.save(usuario);
    }
}
