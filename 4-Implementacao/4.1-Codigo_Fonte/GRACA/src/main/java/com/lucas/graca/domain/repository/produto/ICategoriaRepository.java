/**
 * 
 */
package com.lucas.graca.domain.repository.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.produto.Categoria;

/**
 * @author eits
 *
 */
public interface ICategoriaRepository extends JpaRepository<Categoria, Long>
{
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new Categoria( categoria.id, categoria.nome ) " +
			   "FROM Categoria categoria " +
			  "WHERE (  (FILTER(categoria.nome, :filter) = TRUE)  )"
			)
	public Page<Categoria> listByFilters( @Param("filter") String filters, Pageable pageable );
	
}
