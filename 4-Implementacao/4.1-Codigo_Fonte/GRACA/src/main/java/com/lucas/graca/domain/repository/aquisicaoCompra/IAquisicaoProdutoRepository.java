/**
 * 
 */
package com.lucas.graca.domain.repository.aquisicaoCompra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.aquisicaoCompra.AquisicaoProduto;

/**
 * @author lucas
 *
 */
public interface IAquisicaoProdutoRepository extends JpaRepository<AquisicaoProduto, Long>
{
	
	
}
