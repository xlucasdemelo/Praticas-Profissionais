package com.lucas.graca.domain.repository.questionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.questionario.Resposta;

public interface IRespostaRepository extends JpaRepository<Resposta, Long> 
{
	/**
	 * 
	 * @param questionarioRespostaId
	 * @return
	 */
	public Page<Resposta> findByQuestionarioRespostaId(Long questionarioRespostaId, Pageable pageable);
	
}
