package com.lucas.graca.domain.repository.questionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.questionario.Resposta;

public interface IRespostaRepository extends JpaRepository<Resposta, Long> 
{
	/**
	 * 
	 * @param questionarioRespostaId
	 * @return
	 */
	public Page<Resposta> findByQuestionarioRespostaIdAndQuestaoVersaoQuestionarioId(Long questionarioRespostaId, Long versaoQuestionarioId, Pageable pageable);
	
	/**
	 * 
	 * @param questionarioRespostaId
	 * @param versaoQuestionarioId
	 * @param pageable
	 * @return
	 */
	public Page<Resposta> findByQuestionarioRespostaId(Long questionarioRespostaId, Pageable pageable);
	
	/**
	 * 
	 * @param questionarioRespostaId
	 * @param versaoId
	 * @param pageable
	 */
//	@Query(value="SELECT new Crianca(crianca.id, crianca.nome, crianca.dataNascimento, "
//			+ "crianca.ocupacao, crianca.rendaMensal, crianca.filiacao, "
//			+ "crianca.telefone, crianca.sexo, endereco, crianca.ativo, familia, "
//			+ "crianca.grauEscolaridade, crianca.peso, crianca.motivoAcolhimento, crianca.numeroProcesso, crianca.altura, crianca.dataElaboracaoPIA, "
//			+ "crianca.dataLimite, crianca.dataAcolhimento, crianca.etnia, crianca.entidadeAcolhimento, casaLar ) " +
//			   
//			"FROM Crianca crianca " +
//			   "LEFT OUTER JOIN crianca.endereco endereco " + 
//			   "LEFT OUTER JOIN crianca.familia familia " +
//			   "LEFT OUTER JOIN crianca.casaLar casaLar " +
//			  "WHERE ( crianca.ativo IS TRUE " +
//			   		"AND (FILTER(crianca.nome, :filter) = TRUE) " +
//			  ")"
//			)
//	public listRespostaByQuestionarioRespostaIdAndVersaoId(Long questionarioRespostaId, Long versaoId, Pageable pageable)
	
}
