/**
 * 
 */
package com.lucas.graca.domain.repository.crianca;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.crianca.Parente;

/**
 * @author lucas
 *
 */
public interface IParenteRepository extends JpaRepository<Parente, Long>
{
	
}
