/**
 * 
 */
package com.lucas.graca.domain.repository.questionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
	
}
