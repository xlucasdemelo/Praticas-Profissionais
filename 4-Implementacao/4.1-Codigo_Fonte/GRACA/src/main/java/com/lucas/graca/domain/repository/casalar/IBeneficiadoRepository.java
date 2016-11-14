/**
 * 
 */
package com.lucas.graca.domain.repository.casalar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.casalar.Beneficiado;

/**
 * @author lucas
 *
 */
public interface IBeneficiadoRepository extends JpaRepository<Beneficiado, Long>
{
	/**
	 * 
	 * @param requisicaoCompraId
	 * @param pageable
	 * @return
	 */
	public Page<Beneficiado> findByRequisicaoCompraId( Long requisicaoCompraId, Pageable pageable );
}
