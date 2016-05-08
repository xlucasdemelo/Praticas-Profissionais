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
	
	@Query(value="SELECT new Familia(familia.id, familia.nome, familia.telefone, familia.numeroComodos, familia.situacaoImovel, familia.infraestrutura, familia.tipoImovel, endereco, familia.ativo, familia.nomeMae) " +
			   "FROM Familia familia " +
			   "LEFT OUTER JOIN familia.endereco endereco " + 
			  "WHERE ( familia.ativo = TRUE AND "
			  	 + "( LOWER( familia.nome ) LIKE '%' || LOWER(CAST(:filter AS string))  || '%' OR :filter = NULL ) "
			  	 + "OR ( LOWER( familia.nomeMae) LIKE '%' || LOWER(CAST(:filter AS string))  || '%' OR :filter = NULL ) )"
			)
	public Page<Familia> listByFilters( @Param("filter") String filters, Pageable pageable );
}
