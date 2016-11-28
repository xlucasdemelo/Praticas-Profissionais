package com.lucas.graca.domain.service.evento;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.evento.Evento;
import com.lucas.graca.domain.entity.evento.Papel;
import com.lucas.graca.domain.repository.evento.IEventoRepository;
import com.lucas.graca.domain.repository.evento.IPapelRepository;

@Service
@RemoteProxy
@Transactional
public class EventoService 
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 	@Autowired
	 */
	@Autowired
	private IEventoRepository eventoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IPapelRepository papelRepository;
	
	/*-------------------------------------------------------------------
	 *				 		  SERVICES EVENTO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param evento
	 * @return
	 */
	public Evento insertEvento(Evento evento)
	{
		Assert.notNull(evento, "Evento não pode ser nulo");
		Assert.notNull(evento.getLocal(), "Local é obrigatório");
		Assert.notNull(evento.getData(), "Data é obrigatória");
		Assert.notNull(evento.getHoraFim(), "Horário de fim é obrigatório");
		Assert.notNull(evento.getHoraInicio(), "Hora de início é obrigatório");
		Assert.notNull(evento.getNome(), "Nome é obrigatório");
		Assert.notNull(evento.getDescricao(), "Descrição é obrigatório");
		
		return this.eventoRepository.save(evento);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void disableEvento(long id)
	{
		Evento evento = this.eventoRepository.findOne(id);
		Assert.notNull(evento, "Registro não encontrado");
		
		evento.disableEvento();
		
		this.eventoRepository.save(evento);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void enableEvento(long id)
	{
		Evento evento = this.eventoRepository.findOne(id);
		Assert.notNull(evento, "Registro não encontrado");
		
		evento.enableEvento();
		
		this.eventoRepository.save(evento);
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Evento> listEventosByFilters( String filters, PageRequest pageable )
	{
		return this.eventoRepository.listByFilters(filters, pageable);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Evento findEventoById( long id )
	{
		return this.eventoRepository.findOne(id);
	}
	
	/*-------------------------------------------------------------------
	 *				 		  SERVICES PAPEL
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param papel
	 * @return
	 */
	public Papel insertPapel(Papel papel)
	{
		Assert.notNull(papel);
		
		return this.papelRepository.save(papel);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removePapel( long id )
	{
		Papel papel = this.papelRepository.findOne(id);
		Assert.notNull(papel, "Registro não encontrado");
		
		this.papelRepository.delete(papel);
	}
	
	/**
	 * 
	 * @param eventoId
	 * @param pageable
	 * @return
	 */
	public Page<Papel> listPapeisByEvento( long eventoId, PageRequest pageable )
	{
		return this.papelRepository.findByEventoId(eventoId, pageable);
	}
}
