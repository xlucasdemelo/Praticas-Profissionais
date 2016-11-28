package com.lucas.graca.domain.repository.questionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.questionario.QuestionarioResposta;

public interface IQuestionarioRespostaRepository extends JpaRepository<QuestionarioResposta, Long> 
{
	
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param pageable
	 * @return
	 */
//	@Query(value="SELECT new QuestionarioResposta( questionarioResposta.id, questionarioResposta.dataFinalizacao, questionarioResposta.status, questionarioResposta.versao, usuario ) " +
//			   " FROM QuestionarioResposta questionarioResposta " +
//			   " LEFT OUTER JOIN questionarioResposta.usuario usuario" + 
//			   " WHERE ( usuario.id = :userId " +
//			   			" AND (FILTER(questionarioRespostaquestionario.nome, :filter) = TRUE)" +
//			  " ) "
//			)
//	public Page<Questionario> listQuestionariosByUserAndFilters( @Param("filter") String filter, @Param("userId") Long userId, Pageable pageable );
	
	/**
	 * 
	 * @param usuarioId
	 * @param pageable
	 * @return
	 */
	public Page<QuestionarioResposta> findByUsuarioId( Long usuarioId, Pageable pageable );
	
}
