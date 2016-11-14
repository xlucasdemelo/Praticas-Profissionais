/**
 * 
 */
package com.lucas.graca.domain.repository.casalar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.casalar.RequisicaoCompra;

/**
 * @author lucas
 *
 */
public interface IRequisicaoCompraRepository extends JpaRepository<RequisicaoCompra, Long>
{
	
	/**
	 * 
	 * @param casaLarId
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new RequisicaoCompra( requisicaoCompra.id, requisicaoCompra.descricao, requisicaoCompra.valorDisponibilizado,"
			+ " requisicaoCompra.status, requisicaoCompra.casaLar ) " +
			   "FROM RequisicaoCompra requisicaoCompra " +
			  "WHERE ( requisicaoCompra.casaLar.id = :casaLarId AND (FILTER(requisicaoCompra.descricao, :filter) = TRUE) ) "
			)
	public Page<RequisicaoCompra> listByCasaLarAndFilters( @Param("casaLarId")Long casaLarId, @Param("filter")String filters, Pageable pageable );
	
}
