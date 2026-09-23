package br.com.insper.appprova.controller;

import br.com.insper.appprova.dto.AvaliacaoRequestDTO;
import br.com.insper.appprova.model.Avaliacao;
import br.com.insper.appprova.repository.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AvaliacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        avaliacaoRepository.deleteAll();
    }

    @Test
    void deveCriarAvaliacaoComSucesso() throws Exception {
        AvaliacaoRequestDTO dto = AvaliacaoRequestDTO.builder()
                .autor("pedro")
                .conteudo("Avaliacao basica")
                .nota(4)
                .dataAvaliacao(LocalDate.ofEpochDay(2026- 3 -10))
                .build();

        mockMvc.perform(post("/avaliacoess")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.autor").value("pedro"));
    }

    @Test
    void deveListarAvaliacaosRespeitandoAtivosEFiltroStartsWith() throws Exception {
        avaliacaoRepository.save(Avaliacao.builder().autor("pedro marcos").nota(2).dataAvaliacao(LocalDate.ofEpochDay(2026- 3 -10)).ativo(true).build());
        avaliacaoRepository.save(Avaliacao.builder().autor("pedro ribeiro").nota(4).dataAvaliacao(LocalDate.ofEpochDay(2026- 3 -10)).ativo(true).build());
        avaliacaoRepository.save(Avaliacao.builder().autor("mariana").nota(3).dataAvaliacao(LocalDate.ofEpochDay(2026- 3 -10)).ativo(true).build());
        avaliacaoRepository.save(Avaliacao.builder().autor("jurandir").nota(1).dataAvaliacao(LocalDate.ofEpochDay(2026- 3 -10)).ativo(false).build());

        mockMvc.perform(get("/avaliacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));

        mockMvc.perform(get("/avaliacoes?autor=pedro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @org.junit.jupiter.api.Test
    void deveDeletarAvaliacaoLogicamente() throws Exception {
        Avaliacao avaliacao = avaliacaoRepository.save(
                Avaliacao.builder().autor("Denis").nota(2).dataAvaliacao(LocalDate.ofEpochDay(2026- 3 -10)).ativo(true).build()
        );

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/avaliacoes/" + avaliacao.getId()))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isNoContent());

        org.junit.jupiter.api.Assertions.assertTrue(avaliacaoRepository.findByIdAndAtivoTrue(avaliacao.getId()).isEmpty());
    }
}