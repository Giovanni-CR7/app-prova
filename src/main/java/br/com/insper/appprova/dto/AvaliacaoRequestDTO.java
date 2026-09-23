package br.com.insper.appprova.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AvaliacaoRequestDTO {

    @NotBlank(message = "O autor é obrigatório")
    private String autor;

    @NotBlank(message = "O conteudo é obrigatório")
    private String conteudo;

    @NotNull(message = "A nota é obrigatória")
    private Integer nota;

    @NotNull(message = "A data é obrigatória")
    private LocalDate dataAvaliacao;
}