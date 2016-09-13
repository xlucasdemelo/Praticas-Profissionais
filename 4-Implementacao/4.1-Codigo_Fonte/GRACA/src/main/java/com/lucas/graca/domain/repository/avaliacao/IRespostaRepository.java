/**
 * 
 */
package com.lucas.graca.domain.repository.avaliacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.avaliacaoIndividual.Resposta;

/**
 * @author lucas
 *
 */
public interface IRespostaRepository extends JpaRepository<Resposta, Long>
{
	
	/**
	 * 
	 * @param avaliacaoIndividualId
	 * @param pageable
	 * @return
	 */
	public Page<Resposta> findByAvaliacaoId( Long avaliacaoIndividualId, Pageable pageable );
	
}
