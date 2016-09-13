/**
 * 
 */
package com.lucas.graca.domain.repository.avaliacao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.avaliacaoIndividual.ConfiguracaoAvaliacaoIndividual;

/**
 * @author lucas
 *
 */
public interface IConfiguracaoAvaliacaorepository extends JpaRepository<ConfiguracaoAvaliacaoIndividual, Long>
{

	/**
	 * 
	 * @return
	 */
	public ConfiguracaoAvaliacaoIndividual findTopByIdOrderByIdDesc();
	
}
