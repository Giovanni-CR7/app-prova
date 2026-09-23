package br.com.insper.appprova.service;

import br.com.insper.appprova.dto.AvaliacaoRequestDTO;
import br.com.insper.appprova.dto.AvaliacaoResponseDTO;
import br.com.insper.appprova.model.Avaliacao;
import br.com.insper.appprova.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public AvaliacaoResponseDTO salvar(AvaliacaoRequestDTO dto) {
        Avaliacao avaliacao = Avaliacao.builder()
                .autor(dto.getAutor())
                .conteudo(dto.getConteudo())
                .nota(dto.getNota())
                .dataAvaliacao(dto.getDataAvaliacao())
                .ativo(true)
                .build();

        Avaliacao salvo = avaliacaoRepository.save(avaliacao);
        return AvaliacaoResponseDTO.fromEntity(salvo);
    }

    public List<AvaliacaoResponseDTO> listar(String autor) {
        List<Avaliacao> avaliacaos;
        if (autor != null && !autor.isBlank()) {
            avaliacaos = avaliacaoRepository.findByAutorStartingWithIgnoreCaseAndAtivoTrue(autor);
        } else {
            avaliacaos = avaliacaoRepository.findByAtivoTrue();
        }
        return avaliacaos.stream().map(AvaliacaoResponseDTO::fromEntity).toList();
    }

    public void remover(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Avaliacao não encontrada"));
        avaliacao.setAtivo(false);
        avaliacaoRepository.save(avaliacao);
    }
}