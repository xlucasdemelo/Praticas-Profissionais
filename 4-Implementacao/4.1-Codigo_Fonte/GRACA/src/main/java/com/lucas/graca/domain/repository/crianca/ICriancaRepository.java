/**
 * 
 */
package com.lucas.graca.domain.repository.crianca;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.crianca.Crianca;

/**
 * @author lucas
 *
 */
public interface ICriancaRepository extends JpaRepository<Crianca, Long>
{
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new Crianca(crianca.id, crianca.nome, crianca.dataNascimento, "
			+ "crianca.ocupacao, crianca.rendaMensal, crianca.filiacao, "
			+ "crianca.telefone, crianca.sexo, endereco, crianca.ativo, familia, "
			+ "crianca.grauEscolaridade, crianca.peso, crianca.motivoAcolhimento, crianca.numeroProcesso, crianca.altura, crianca.dataElaboracaoPIA, "
			+ "crianca.dataLimite, crianca.dataAcolhimento, crianca.etnia, crianca.entidadeAcolhimento, casaLar ) " +
			   
			"FROM Crianca crianca " +
			   "LEFT OUTER JOIN crianca.endereco endereco " + 
			   "LEFT OUTER JOIN crianca.familia familia " +
			   "LEFT OUTER JOIN crianca.casaLar casaLar " +
			  "WHERE ( crianca.ativo IS TRUE " +
			   		"AND (FILTER(crianca.nome, :filter) = TRUE) " +
			  ")"
			)
	public Page<Crianca> listCriancasByFilters( @Param("filter") String filters, Pageable pageable );
	
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new Crianca(crianca.id, crianca.nome, crianca.dataNascimento, "
			+ "crianca.ocupacao, crianca.rendaMensal, crianca.filiacao, "
			+ "crianca.telefone, crianca.sexo, endereco, crianca.ativo, familia, "
			+ "crianca.grauEscolaridade, crianca.peso, crianca.motivoAcolhimento, crianca.numeroProcesso, crianca.altura, crianca.dataElaboracaoPIA, "
			+ "crianca.dataLimite, crianca.dataAcolhimento, crianca.etnia, crianca.entidadeAcolhimento, casaLar ) " +
			   
			"FROM Crianca crianca " +
			   "LEFT OUTER JOIN crianca.endereco endereco " + 
			   "LEFT OUTER JOIN crianca.familia familia " +
			   "LEFT OUTER JOIN crianca.casaLar casaLar " +
			  "WHERE ( " +
			  	" FILTER(crianca.ativo, :ativo) = TRUE  " +
		   		" OR FILTER(crianca.ativo, :inativo) = FALSE " +
			  ")"
			)
	public Page<Crianca> listCriancasByMoreFilters( @Param("ativo") boolean ativo, @Param("inativo") boolean inativo, Pageable pageable );
	
	/**
	 * 
	 * @param familiaId
	 * @param pageable
	 * @return
	 */
	public Page<Crianca> findByFamiliaId(Long familiaId, Pageable pageable);
	
	/**
	 * 
	 * @param casaLarId
	 * @param pageable
	 * @return
	 */
	public Page<Crianca> findByCasaLarId(long casaLarId, Pageable pageable);
	
}
