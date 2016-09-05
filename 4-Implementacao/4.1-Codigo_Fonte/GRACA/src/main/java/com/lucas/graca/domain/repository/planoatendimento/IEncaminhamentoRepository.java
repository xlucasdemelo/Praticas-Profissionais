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

import com.lucas.graca.domain.entity.planoatendimento.Encaminhamento;
import com.lucas.graca.domain.entity.planoatendimento.TipoEncaminhamento;

/**
 * @author lucas
 *
 */
public interface IEncaminhamentoRepository extends JpaRepository<Encaminhamento, Long>
{
	
	@Query(value="SELECT new Encaminhamento( encaminhamento.id, encaminhamento.descricao, encaminhamento.observacao, encaminhamento.status, "
			+ "encaminhamento.planoAtendimentoFamiliar, encaminhamento.usuario, encaminhamento.integranteFamiliar, "
			+ "encaminhamento.dataFinal, encaminhamento.responsavel, encaminhamento.tipo )" +
			   " FROM Encaminhamento encaminhamento " +
			   " LEFT OUTER JOIN encaminhamento.integranteFamiliar " + 
			   " WHERE ( encaminhamento.planoAtendimentoFamiliar.id = :idPlanoAtendimento " +
			   			" AND encaminhamento.tipo = :tipo " +
			   			" AND (FILTER(encaminhamento.descricao, :filter) = TRUE)" +
			  " ) "
			)
	public Page<Encaminhamento> listByPlanoAtendimentoAndFilters( @Param("idPlanoAtendimento") long idPlanoAtendimento, @Param("filter") String filter, @Param("tipo") TipoEncaminhamento tipo, Pageable pageable );
	
	public List<Encaminhamento> findByPlanoAtendimentoFamiliarId( Long planoAtendimentoFamiliarId );
}
