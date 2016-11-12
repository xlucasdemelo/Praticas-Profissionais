/**
 * 
 */
package com.lucas.graca.domain.repository.casalar;

import java.time.YearMonth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.casalar.OrcamentoFamiliar;

/**
 * @author lucas
 *
 */
public interface IOrcamentoFamiliarRepository extends JpaRepository<OrcamentoFamiliar, Long>
{
	
	/**
	 * 
	 * @param casaLarId
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new OrcamentoFamiliar( orcamentoFamiliar.id, orcamentoFamiliar.periodo, "
			+ "orcamentoFamiliar.rendaPerCapitaAlimentacao, orcamentoFamiliar.rendaPerCapitaHigiene, "
			+ "orcamentoFamiliar.status, orcamentoFamiliar.casaLar) " +
			   "FROM OrcamentoFamiliar orcamentoFamiliar " +
			  "WHERE ( orcamentoFamiliar.casaLar.id = :casaLarId  )"
			)
	public Page<OrcamentoFamiliar> listOrcamentosFamiliaresByCasaLar(@Param("casaLarId") Long casaLarId, Pageable pageable);
	
	/**
	 * 
	 * @return
	 */
	@Query(value="SELECT new OrcamentoFamiliar( orcamentoFamiliar.id, orcamentoFamiliar.periodo, "
			+ "orcamentoFamiliar.rendaPerCapitaAlimentacao, orcamentoFamiliar.rendaPerCapitaHigiene, "
			+ "orcamentoFamiliar.status, orcamentoFamiliar.casaLar) " +
			   "FROM OrcamentoFamiliar orcamentoFamiliar " +
			  "WHERE ( orcamentoFamiliar.casaLar.id = :casaLarId AND orcamentoFamiliar.periodo = :periodo AND orcamentoFamiliar.status = 2)"
			)
	public OrcamentoFamiliar findByCasaLarAndPeriodoAndStatusVigente( @Param("casaLarId") Long casaLarId, @Param("periodo") YearMonth periodo);
}
