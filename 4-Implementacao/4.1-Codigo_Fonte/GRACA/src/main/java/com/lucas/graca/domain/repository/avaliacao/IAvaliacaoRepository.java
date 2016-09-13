/**
 * 
 */
package com.lucas.graca.domain.repository.avaliacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.avaliacaoIndividual.AvaliacaoIndividual;

/**
 * @author lucas
 *
 */
public interface IAvaliacaoRepository extends JpaRepository<AvaliacaoIndividual, Long>
{
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new AvaliacaoIndividual( avaliacaoIndividual.id, avaliacaoIndividual.status, avaliacaoIndividual.questionario, crianca, avaliacaoIndividual.avaliador, avaliacaoIndividual.enabled ) " +
			   " FROM AvaliacaoIndividual avaliacaoIndividual " +
			   " LEFT OUTER JOIN avaliacaoIndividual.crianca crianca" + 
			   " WHERE (  " +
			   			" (FILTER(crianca.nome, :filter) = TRUE)" +
			  " ) "
			)
	public Page<AvaliacaoIndividual> listAvaliacoesFilters( @Param("filter") String filter, Pageable pageable );
	
}
