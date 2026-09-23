package br.com.insper.appprova.controller;

import br.com.insper.appprova.dto.AvaliacaoRequestDTO;
import br.com.insper.appprova.dto.AvaliacaoResponseDTO;
import br.com.insper.appprova.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoResponseDTO criarAvaliacao(@RequestBody @Valid AvaliacaoRequestDTO dto) {
        return avaliacaoService.salvar(dto);
    }

    @GetMapping
    public List<AvaliacaoResponseDTO> listarAvaliacaos(@RequestParam(required = false) String nome) {
        return avaliacaoService.listar(nome);
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deletarAvaliacao(@org.springframework.web.bind.annotation.PathVariable Long id) {
        avaliacaoService.remover(id);
    }
}