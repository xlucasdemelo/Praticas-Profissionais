/**
 * 
 */
package com.digows.blank.domain.repository.crianca;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digows.blank.domain.entity.crianca.Parente;

/**
 * @author lucas
 *
 */
public interface IParenteRepository extends JpaRepository<Parente, Long>
{
	
}
