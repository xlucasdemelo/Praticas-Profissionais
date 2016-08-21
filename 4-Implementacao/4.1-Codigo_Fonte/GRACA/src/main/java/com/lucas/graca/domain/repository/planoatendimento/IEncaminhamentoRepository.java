/**
 * 
 */
package com.lucas.graca.domain.repository.planoatendimento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.planoatendimento.Encaminhamento;
import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;

/**
 * @author lucas
 *
 */
public interface IEncaminhamentoRepository extends JpaRepository<Encaminhamento, Long>
{
	
	@Query(value="SELECT new Encaminhamento( encaminhamento.id, encaminhamento.descricao, encaminhamento.observacao, encaminhamento.status, encaminhamento.planoAtendimento, encaminhamento.usuario, encaminhamento.integranteFamiliar )" +
			   "FROM Encaminhamento encaminhamento " +
			   "LEFT OUTER JOIN encaminhamento.integranteFamiliar " + 
			  "WHERE ( encaminhamento.planoAtendimento.id = :idPlanoAtendimento " +
			  " ) "
			)
	public Page<Encaminhamento> listByPlanoAtendimentoAndFilters( @Param("idPlanoAtendimento") long idPlanoAtendimento, @Param("filter") String filter, Pageable pageable );
	
}
