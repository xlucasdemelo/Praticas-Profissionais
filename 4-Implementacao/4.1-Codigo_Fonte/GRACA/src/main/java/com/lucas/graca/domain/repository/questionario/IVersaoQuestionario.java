/**
 * 
 */
package com.lucas.graca.domain.repository.questionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.questionario.VersaoQuestionario;

/**
 * @author lucas
 *
 */
public interface IVersaoQuestionario extends JpaRepository<VersaoQuestionario, Long>
{
	
	/**
	 * 
	 * @param questionarioId
	 * @param pageable
	 * @return
	 */
	Page<VersaoQuestionario> findByQuestionarioId ( Long questionarioId, Pageable pageable );
	
}
