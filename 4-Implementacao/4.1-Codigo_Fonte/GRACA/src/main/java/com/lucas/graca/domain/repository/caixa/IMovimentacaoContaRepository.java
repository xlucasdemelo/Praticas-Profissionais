/**
 * 
 */
package com.lucas.graca.domain.repository.caixa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.caixa.MovimentacaoCaixa;

/**
 * @author eits
 *
 */
public interface IMovimentacaoContaRepository extends JpaRepository<MovimentacaoCaixa, Long>
{
	
	@Query(value="SELECT new MovimentacaoCaixa( movimentacaoCaixa.id, movimentacaoCaixa.valor, movimentacaoCaixa.contaBancaria, movimentacaoCaixa.movimentacao ) " +
			   "FROM MovimentacaoCaixa movimentacaoCaixa " +
			  "WHERE ( movimentacaoCaixa.movimentacao.id = :movimentacaoId )"
			)
	public Page<MovimentacaoCaixa> listByMovimentacao( @Param("movimentacaoId") Long movimentacaoId, Pageable pageable );
	
}
