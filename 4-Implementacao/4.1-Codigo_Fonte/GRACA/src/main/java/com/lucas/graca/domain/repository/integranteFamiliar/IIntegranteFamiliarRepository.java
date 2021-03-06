/**
 * 
 */
package com.lucas.graca.domain.repository.integranteFamiliar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.integrantefamiliar.IntegranteFamiliar;

/**
 * @author lucas
 *
 */
public interface IIntegranteFamiliarRepository extends JpaRepository<IntegranteFamiliar, Long>
{
	/**
	 * 
	 * @param familiaId
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new IntegranteFamiliar(integranteFamiliar.id, integranteFamiliar.nome, integranteFamiliar.dataNascimento, integranteFamiliar.ocupacao, integranteFamiliar.rendaMensal, integranteFamiliar.filiacao, integranteFamiliar.telefone, integranteFamiliar.sexo, endereco, integranteFamiliar.ativo, familia, integranteFamiliar.grauEscolaridade ) " +
			   "FROM IntegranteFamiliar integranteFamiliar " +
			   "LEFT OUTER JOIN integranteFamiliar.endereco endereco " + 
			   "LEFT OUTER JOIN integranteFamiliar.familia familia " +
			  "WHERE ( integranteFamiliar.ativo IS TRUE AND familia.id = :familiaId )"
			)
	public Page<IntegranteFamiliar> listIntegrantesByfamilia( @Param("familiaId") Long familiaId, Pageable pageable );
	
	/**
	 * 
	 * @param familiaId
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new IntegranteFamiliar(integranteFamiliar.id, integranteFamiliar.nome, integranteFamiliar.dataNascimento, integranteFamiliar.ocupacao, integranteFamiliar.rendaMensal, integranteFamiliar.filiacao, integranteFamiliar.telefone, integranteFamiliar.sexo, endereco, integranteFamiliar.ativo, familia, integranteFamiliar.grauEscolaridade ) " +
			   "FROM IntegranteFamiliar integranteFamiliar " +
			   "LEFT OUTER JOIN integranteFamiliar.endereco endereco " + 
			   "LEFT OUTER JOIN integranteFamiliar.familia familia " +
			  "WHERE ( integranteFamiliar.ativo IS TRUE AND familia.id = :familiaId )" +
			   " AND (FILTER(integranteFamiliar.nome, :filter) = TRUE)"
			)
	public Page<IntegranteFamiliar> listIntegrantesByfamiliaAndFilters( @Param("familiaId") Long familiaId, @Param("filter")String filter, Pageable pageable );
	
	/**
	 * 
	 * @param familiaId
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new IntegranteFamiliar(integranteFamiliar.id, integranteFamiliar.nome, integranteFamiliar.dataNascimento, integranteFamiliar.ocupacao, integranteFamiliar.rendaMensal, integranteFamiliar.filiacao, integranteFamiliar.telefone, integranteFamiliar.sexo, endereco, integranteFamiliar.ativo, familia, integranteFamiliar.grauEscolaridade ) " +
			   "FROM IntegranteFamiliar integranteFamiliar " +
			   "LEFT OUTER JOIN integranteFamiliar.endereco endereco " + 
			   "LEFT OUTER JOIN integranteFamiliar.familia familia " +
			  "WHERE ( integranteFamiliar.id <> :criancaId AND ( integranteFamiliar.ativo IS TRUE AND familia.id = :familiaId )" +
			  		")"
			)
	public Page<IntegranteFamiliar> listIntegrantesByfamiliaToCrianca( @Param("familiaId") Long familiaId,  @Param("criancaId") Long criancaId, Pageable pageable );
}
