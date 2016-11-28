package com.lucas.graca.domain.repository.evento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.evento.Papel;

public interface IPapelRepository extends JpaRepository<Papel, Long> 
{
	
	/**
	 * 
	 * @param eventoId
	 * @param pageable
	 * @return
	 */
	public Page<Papel> findByEventoId(Long eventoId, Pageable pageable);
	
}
