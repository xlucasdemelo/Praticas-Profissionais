package com.lucas.graca.domain.repository.evento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.evento.Evento;

public interface IEventoRepository extends JpaRepository<Evento, Long> 
{
	
	@Query(value="SELECT new Evento( evento.id, evento.local, evento.data, evento.horaInicio, evento.horaFim, evento.descricao, evento.nome, "
			+ "evento.enabled ) " +
			   "FROM Evento evento " +
			  "WHERE (  "
			  	 + " (FILTER(evento.nome, :filter) = TRUE)  "
			  	 + ")"
			)
		public Page<Evento> listByFilters( @Param("filter")String filters, Pageable pageable );
	
}
