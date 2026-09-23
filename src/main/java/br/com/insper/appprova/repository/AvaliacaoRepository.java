package br.com.insper.appprova.repository;

import br.com.insper.appprova.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    // Lista apenas as avaliacoes ativas (não deletadas)
    List<Avaliacao> findByAtivoTrue();

    // Filtro startsWith ignorando maiúsculas/minúsculas para os ativos
    List<Avaliacao> findByAutorStartingWithIgnoreCaseAndAtivoTrue(String autor);

    // Busca por id apenas se ativo
    Optional<Avaliacao> findByIdAndAtivoTrue(Long id);
}