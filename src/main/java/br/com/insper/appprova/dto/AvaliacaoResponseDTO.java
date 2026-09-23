package br.com.insper.appprova.dto;

import br.com.insper.appprova.model.Avaliacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoResponseDTO {

    private Long id;
    private String autor;
    private String conteudo;
    private Integer nota;
    private LocalDate dataAvaliacao;

    public static AvaliacaoResponseDTO fromEntity(Avaliacao avaliacao) {
        return AvaliacaoResponseDTO.builder()
                .id(avaliacao.getId())
                .autor(avaliacao.getAutor())
                .conteudo(avaliacao.getConteudo())
                .nota(avaliacao.getNota())
                .dataAvaliacao(avaliacao.getDataAvaliacao())
                .build();
    }
}