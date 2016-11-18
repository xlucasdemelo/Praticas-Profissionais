/**
 * 
 */
package com.lucas.graca.domain.repository.aquisicaoCompra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.aquisicaoCompra.ProdutoAdquirido;

/**
 * @author lucas
 *
 */
public interface IProdutoAdquiridoRepository extends JpaRepository<ProdutoAdquirido, Long>
{
	
	/**
	 * 
	 * @param fornecedorAquisicaoId
	 * @param pageable
	 * @return
	 */
	public Page<ProdutoAdquirido> findByAquisicaoProdutoId(Long aquisicaoProdutoId, Pageable pageable);
	
}
