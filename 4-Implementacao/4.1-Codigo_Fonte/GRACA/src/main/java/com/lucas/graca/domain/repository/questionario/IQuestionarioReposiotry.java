/**
 * 
 */
package com.lucas.graca.domain.repository.questionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.questionario.Questionario;

/**
 * @author lucas
 *
 */
public interface IQuestionarioReposiotry extends JpaRepository<Questionario, Long>
{
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new Questionario( questionario.id, questionario.nome, questionario.descricao, usuarioCriador ) " +
			   " FROM Questionario questionario " +
			   " LEFT OUTER JOIN questionario.usuarioCriador usuarioCriador " + 
			   " WHERE (  " +
			   			" (FILTER(questionario.nome, :filter) = TRUE)" +
			  " ) "
			)
	public Page<Questionario> listQuestionariosByFilters( @Param("filter") String filter , Pageable pageable );
	
}
