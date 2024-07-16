package br.com.forum.desafio.topico.controllers;

import br.com.forum.desafio.topico.dto.CriarTopicoDto;
import br.com.forum.desafio.topico.dto.TopicoDto;
import br.com.forum.desafio.topico.services.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;


    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> criar(@RequestBody @Valid CriarTopicoDto dto, UriComponentsBuilder builder){
        var novoTopico = service.criarTopico(dto);
        var uri = builder.path("/topicos/{id}").buildAndExpand(novoTopico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(novoTopico));
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@RequestBody @Valid CriarTopicoDto dto,
                                               @PathVariable @NotNull Long id){
        var topicoAtualizado = service.atualizarTopico(dto, id);
        return ResponseEntity.ok(topicoAtualizado);
    }


    @GetMapping("/{id}")
    public TopicoDto buscarPorId(@PathVariable @NotNull Long id){
        return service.buscarPorId(id);
    }


    @GetMapping("/curso/{curso}")
    public ResponseEntity<Page<TopicoDto>> buscarPorCurso(
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable pageable,
            @PathVariable @NotNull String curso){

        var page = service.buscarPorCurso(curso,pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<Page<TopicoDto>> buscarPorAno(
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable pageable,
            @PathVariable @NotNull Integer ano){

        var page = service.buscarPorAno(ano,pageable);
        return ResponseEntity.ok(page);
    }


    @GetMapping()
    public ResponseEntity<Page<TopicoDto>> buscarTodosTopicos(@PageableDefault(
            size = 10, sort = {"dataCriacao"}
    ) Pageable pageable){
        var page = service.buscarTodos(pageable);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public TopicoDto deletar(@PathVariable @NotNull Long id){
        return service.deletarPorId(id);
    }
}
