package com.lucas.graca.domain.repository.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.produto.Repasse;

public interface IRepasseRepository extends JpaRepository<Repasse, Long>
{
	
	/**
	 * 
	 * @param casaLarId
	 * @param pageable
	 * @return
	 */
	public Page<Repasse> findByCasaLarId(Long casaLarId, Pageable pageable);
	
	
	/**
	 * 
	 * @return
	 */
	public Page<Repasse> findByProdutoId(Long produtoId, Pageable pageable);
	
}
