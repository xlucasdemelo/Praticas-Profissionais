package com.lucas.graca.domain.repository.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.produto.ProdutoRepassado;

public interface IProdutoRepassadoRepository extends JpaRepository<ProdutoRepassado, Long> 
{
	
	/**
	 * 
	 * @param casaLarId
	 * @param pageable
	 * @return
	 */
	public Page<ProdutoRepassado> findByRepasseId(Long repasseId, Pageable pageable);
	
	
	/**
	 * 
	 * @param produtoId
	 * @param pageable
	 * @return
	 */
	public Page<ProdutoRepassado> findByProdutoId(Long produtoId, Pageable pageable);
	
}
