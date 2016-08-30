/**
 * 
 */
package com.lucas.graca.domain.repository.planoatendimento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;

/**
 * @author lucas
 *
 */
public interface IPlanoAtendimentoFamiliarRepository extends JpaRepository<PlanoAtendimentoFamiliar, Long>
{
	
	@Query(value="SELECT new PlanoAtendimentoFamiliar(planoAtendimentoFamiliar.id, planoAtendimentoFamiliar.ativo, planoAtendimentoFamiliar.status, planoAtendimentoFamiliar.familia, planoAtendimentoFamiliar.motivoFinalizacao) " +
			   "FROM PlanoAtendimentoFamiliar planoAtendimentoFamiliar " +
			   "LEFT OUTER JOIN planoAtendimentoFamiliar.familia " + 
			  "WHERE ( planoAtendimentoFamiliar.ativo = :ativo AND "
			  	 + "( (FILTER(planoAtendimentoFamiliar.familia.nome, :filter) = TRUE)  "
			  	 + "OR ( FILTER(planoAtendimentoFamiliar.familia.nomeMae, :filter) = TRUE ) ) )"
			)
	public Page<PlanoAtendimentoFamiliar> listByFilters( @Param("filter") String filter, @Param("ativo") Boolean ativo, Pageable pageable );
	
	@Query(value="SELECT new PlanoAtendimentoFamiliar(planoAtendimentoFamiliar.id, planoAtendimentoFamiliar.ativo, planoAtendimentoFamiliar.status, planoAtendimentoFamiliar.familia, planoAtendimentoFamiliar.motivoFinalizacao) " +
			   "FROM PlanoAtendimentoFamiliar planoAtendimentoFamiliar " +
			   "LEFT OUTER JOIN planoAtendimentoFamiliar.familia " + 
			  "WHERE ( planoAtendimentoFamiliar.familia.id = :id )"
			)
	public List<PlanoAtendimentoFamiliar> findByFamiliaId(@Param("id") long id);
	
}
