package br.com.insper.appprova.service;

import br.com.insper.appprova.dto.AvaliacaoRequestDTO;
import br.com.insper.appprova.dto.AvaliacaoResponseDTO;
import br.com.insper.appprova.model.Avaliacao;
import br.com.insper.appprova.repository.AvaliacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    private Avaliacao avaliacao;
    private AvaliacaoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        avaliacao = Avaliacao.builder()
                .id(1L)
                .autor("zambao")
                .conteudo("conteudo")
                .nota(5)
                .dataAvaliacao(LocalDate.ofEpochDay(2026- 3 -10))
                .ativo(true)
                .build();

        requestDTO = AvaliacaoRequestDTO.builder()
                .autor("zambao")
                .conteudo("conteudo")
                .nota(5)
                .dataAvaliacao(LocalDate.ofEpochDay(2026- 3 -10))
                .build();
    }

    @Test
    void deveSalvarAvaliacaoComSucesso() {
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

        AvaliacaoResponseDTO response = avaliacaoService.salvar(requestDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("zambao", response.getAutor());
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    void deveListarTodosQuandoFiltroForNuloOuEmBranco() {
        when(avaliacaoRepository.findByAtivoTrue()).thenReturn(List.of(avaliacao));

        List<AvaliacaoResponseDTO> retornoNulo = avaliacaoService.listar(null);
        assertEquals(1, retornoNulo.size());

        List<AvaliacaoResponseDTO> retornoBranco = avaliacaoService.listar("   ");
        assertEquals(1, retornoBranco.size());

        verify(avaliacaoRepository, times(2)).findByAtivoTrue();
    }

    @Test
    void deveListarComFiltroPorAutor() {
        when(avaliacaoRepository.findByAutorStartingWithIgnoreCaseAndAtivoTrue("zam"))
                .thenReturn(List.of(avaliacao));

        List<AvaliacaoResponseDTO> retorno = avaliacaoService.listar("zam");

        assertEquals(1, retorno.size());
        assertEquals("zambao", retorno.get(0).getAutor());
        verify(avaliacaoRepository, times(1)).findByAutorStartingWithIgnoreCaseAndAtivoTrue("zam");
    }

    @org.junit.jupiter.api.Test
    void deveInativarAvaliacaoComSucesso() {
        org.mockito.Mockito.when(avaliacaoRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(java.util.Optional.of(avaliacao));

        avaliacaoService.remover(1L);

        org.junit.jupiter.api.Assertions.assertFalse(avaliacao.getAtivo());
        org.mockito.Mockito.verify(avaliacaoRepository, org.mockito.Mockito.times(1)).save(avaliacao);
    }

    @org.junit.jupiter.api.Test
    void deveLancarExceptionAoRemoverAvaliacaoInexistente() {
        org.mockito.Mockito.when(avaliacaoRepository.findByIdAndAtivoTrue(99L))
                .thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                org.springframework.web.server.ResponseStatusException.class,
                () -> avaliacaoService.remover(99L)
        );
    }
}