/**
 * 
 */
package com.lucas.graca.domain.repository.fornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.fornecedor.Fornecedor;

/**
 * @author lucas
 *
 */
public interface IFornecedorRepository extends JpaRepository<Fornecedor, Long>
{
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new Fornecedor( fornecedor.id, fornecedor.razaoSocial, fornecedor.cnpj, fornecedor.telefone, fornecedor.ativo, fornecedor.responsavel ) " +
			   "FROM Fornecedor fornecedor " +
			  "WHERE ( fornecedor.ativo = :ativo AND "
			  	 + "( (FILTER(fornecedor.razaoSocial, :filter) = TRUE)  ) )"
			)
	public Page<Fornecedor> listByFilters( @Param("filter") String filters, @Param("ativo") Boolean ativo, Pageable pageable );

}
