/**
 * 
 */
package com.lucas.graca.domain.repository.questionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.questionario.Questao;

/**
 * @author lucas
 *
 */
public interface IQuestaoRepository extends JpaRepository<Questao, Long>
{
	
	/**
	 * 
	 * @param versaoQuestionarioId
	 * @param pageable
	 * @return
	 */
	Page<Questao> findByVersaoQuestionarioId ( Long versaoQuestionarioId, Pageable pageable );
	
	
	@Query(value="SELECT new Questao( questao.id, questao.descricao, questao.tipoQuestao, versao) " + 
			   
			"FROM Questao questao " +
			   "LEFT OUTER JOIN questao.versaoQuestionario versao " +
			  "WHERE ( versao.questionario.id = :questionarioId" +
			  ")"
			)
	Page<Questao> listQuestoesByQuestionario( @Param("questionarioId")Long questionarioId, Pageable pageable  );
	
}
