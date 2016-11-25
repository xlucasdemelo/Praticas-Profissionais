/**
 * 
 */
package com.lucas.graca.domain.repository.caixa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.caixa.Conta;

/**
 * @author eits
 *
 */
public interface IContaRepository extends JpaRepository<Conta, Long>
{
	
	@Query(value="SELECT new Conta( conta.id, conta.descricao, conta.nome, conta.saldo, conta.enabled ) " +
			   "FROM Conta conta " +
			  "WHERE ( conta.enabled = :ativo AND "
			  	 	+ "( (FILTER(conta.descricao, :filter) = TRUE) OR (FILTER(conta.nome, :filter)= TRUE) )"
			  	 + ")"
			)
	public Page<Conta> listByFilters( @Param("filter") String filters, @Param("ativo") Boolean ativo, Pageable pageable );
	
	/**
	 * 
	 * @param nome
	 * @param enabled
	 * @return
	 */
	public Conta findByNomeAndEnabled( String nome, Boolean enabled );
	
}
