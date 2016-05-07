/**
 * 
 */
package com.digows.blank.domain.repository.familia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digows.blank.domain.entity.familia.Familia;

/**
 * @author lucas
 *
 */
public interface IFamiliaRepository extends JpaRepository<Familia, Long>
{
	
	@Query(value="SELECT new Familia(familia.id, familia.nome, familia.telefone, familia.numeroComodos, familia.situacaoImovel, familia.infraestrutura, familia.tipoImovel, familia.endereco, familia.ativo, familia.nomeMae) " +
			   "FROM Familia familia " +
			  "WHERE ( FILTER(familia.id, :filters) = TRUE "
			  	 + "OR FILTER(familia.nome, :filters) = TRUE "
			  	 + "OR FILTER(familia.nomeMae, :filters) = TRUE )"
			)
	public Page<Familia> listByFilters( @Param("filters") String filters, Pageable pageable );
}
