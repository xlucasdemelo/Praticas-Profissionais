/**
 * 
 */
package com.lucas.graca.domain.repository.casalar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.casalar.FornecedorRequisicao;

/**
 * @author lucas
 *
 */
public interface IFornecedorRequisicaoRepository extends JpaRepository<FornecedorRequisicao, Long>
{

	/**
	 * 
	 * @param requisicaoCompraId
	 * @param pageable
	 * @return
	 */
	public Page<FornecedorRequisicao> findByRequisicaoCompraId( Long requisicaoCompraId, Pageable pageable );
	
}
