/**
 * 
 */
package com.digows.blank.domain.repository.crianca;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digows.blank.domain.entity.crianca.Crianca;

/**
 * @author lucas
 *
 */
public interface ICriancaRepository extends JpaRepository<Crianca, Long>
{
	
	@Query(value="SELECT new Crianca(crianca.id, crianca.nome, crianca.dataNascimento, "
			+ "crianca.ocupacao, crianca.rendaMensal, crianca.filiacao, "
			+ "crianca.telefone, crianca.sexo, endereco, crianca.ativo, familia, "
			+ "crianca.grauEscolaridade, crianca.peso, crianca.motivoAcolhimento, crianca.numeroProcesso, crianca.altura, crianca.dataElaboracaoPIA, "
			+ "crianca.dataLimite, crianca.dataAcolhimento, crianca.etnia ) " +
			   
			"FROM Crianca crianca " +
			   "LEFT OUTER JOIN crianca.endereco endereco " + 
			   "LEFT OUTER JOIN crianca.familia familia " +
			  "WHERE ( crianca.ativo IS TRUE " +
			   		"AND FILTER(crianca.nome, :filter) = TRUE) " +
			  ")"
			)
	public Page<Crianca> listCriancasByFilters( @Param("filter") String filters, Pageable pageable );
	
}
