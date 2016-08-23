package com.lucas.graca.domain.entity.account;

import org.directwebremoting.annotations.DataTransferObject;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author rodrigo.p.fraga@gmail.com
 * @since 02/06/2014
 * @version 1.0
 */
@DataTransferObject(type = "enum")
public enum UserRole implements GrantedAuthority
{
	/*-------------------------------------------------------------------
	 *				 		     ENUMS
	 *-------------------------------------------------------------------*/
	ADMINISTRATOR,                      // 0
	ATENDENTE,							// 1
	COLABORADOR_EXTERNO,				// 2
	OPERADOR_ATENDIMENTOS,				// 3
	CHEFE_ADMINISTRACAO,				// 4
	OPERADOR_ADMINISNTRATIVO;			// 5

	public static final String ADMINISTRATOR_VALUE 	= "ADMINISTRATOR";
	public static final String ATENDENTE_VALUE 		= "ATENDENTE";
	public static final String COLABORADOR_EXTERNO_VALUE 			= "COLABORADOR_EXTERNO";
	public static final String OPERADOR_ATENDIMENTOS_VALUE 			= "OPERADOR_ATENDIMENTOS";
	public static final String CHEFE_ADMINISTRACAO_VALUE 			= "CHEFE_ADMINISTRACAO";
	public static final String OPERADOR_ADMINISNTRATIVO_VALUE			= "OPERADOR_ADMINISNTRATIVO"; 
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority()
	{
		return this.name();
	}
}