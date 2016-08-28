/**
 * 
 */
package com.lucas.graca.domain.repository.planoatendimento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.planoatendimento.Responsavel;

/**
 * @author lucas
 *
 */
public interface IResponsavelRepository extends JpaRepository<Responsavel, Long>
{
	
	@Query(value="SELECT new Responsavel( responsavel.id, responsavel.nome, responsavel.telefone, responsavel.cpf, responsavel.email ) " +
			   "FROM Responsavel responsavel " +
			  "WHERE (  (FILTER(responsavel.nome, :filter) = TRUE)  "
			  	 + "OR ( FILTER(responsavel.email, :filter) = TRUE ) ) "
			)
	public List<Responsavel> listByFilters( @Param("filter") String filter);
	
}
