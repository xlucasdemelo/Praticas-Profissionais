package com.lucas.graca.domain.repository.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.produto.Repasse;

public interface IRepasseRepository extends JpaRepository<Repasse, Long>
{
	
	@Query(value="SELECT new Repasse( repasse.id, repasse.status, repasse.casaLar) " +
			   "FROM Repasse repasse " +
			  "WHERE (  (FILTER(repasse.casaLar.cuidadoraResidente.nome, :filters) = TRUE)"
			  	 + ")"
			)
	public Page<Repasse> listByFilters( @Param("filters")String filters, Pageable pageable );
	
}
