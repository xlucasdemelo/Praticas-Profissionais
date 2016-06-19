/**
 * 
 */
package com.lucas.graca.domain.repository.familia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.familia.Familia;

/**
 * @author lucas
 *
 */
public interface IFamiliaRepository extends JpaRepository<Familia, Long>
{
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new Familia(familia.id, familia.nome, familia.telefone, familia.numeroComodos, familia.situacaoImovel, familia.infraestrutura, familia.tipoImovel, familia.tipoMoradia, endereco, familia.ativo, familia.nomeMae, familia.numeroDormitorios) " +
			   "FROM Familia familia " +
			   "LEFT OUTER JOIN familia.endereco endereco " + 
			  "WHERE ( familia.ativo IS TRUE AND "
			  	 + "( (FILTER(familia.nome, :filter) = TRUE)  "
			  	 + "OR ( FILTER(familia.nomeMae, :filter) = TRUE ) ) )"
			)
	public Page<Familia> listByFilters( @Param("filter") String filters, Pageable pageable );
	
	@Query(value="SELECT new Familia(familia.id, familia.nome, familia.telefone, familia.numeroComodos, familia.situacaoImovel, familia.infraestrutura, familia.tipoImovel, familia.tipoMoradia, endereco, familia.ativo, familia.nomeMae, familia.numeroDormitorios) " +
			   "FROM Familia familia " +
			   "LEFT OUTER JOIN familia.endereco endereco " + 
			   "WHERE ( " +
			   		" FILTER(familia.ativo, :ativo) = TRUE  " +
			   		" OR FILTER(familia.ativo, :inativo) = FALSE " +
			   ")"
			)
	public Page<Familia> listByMoreFilters( @Param("ativo") boolean ativo, @Param("inativo") boolean inativo, Pageable pageable );
}
