/**
 * 
 */
package com.lucas.graca.domain.repository.planoatendimento;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.planoatendimento.Parecer;
import com.lucas.graca.domain.entity.planoatendimento.TipoEncaminhamento;

/**
 * @author lucas
 *
 */
public interface IParecerRepository extends JpaRepository<Parecer, Long>
{

	@Query(value="SELECT new Parecer( parecer.id, parecer.descricao, parecer.tipo, parecer.usuario, parecer.planoAtendimentoFamiliar )" +
			   " FROM Parecer parecer" +
			   " WHERE ( parecer.planoAtendimentoFamiliar.id = :idPlanoAtendimento " +
			   			" AND parecer.tipo = :tipo " +
			  " ) "
			)
	public List<Parecer> listByPlanoAtendimentoAndFilters( @Param("idPlanoAtendimento") long idPlanoAtendimento, @Param("tipo") TipoEncaminhamento tipo, Pageable pageable );
	
}
