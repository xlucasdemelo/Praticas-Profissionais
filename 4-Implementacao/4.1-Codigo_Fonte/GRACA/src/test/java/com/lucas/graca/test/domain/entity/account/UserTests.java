package com.lucas.graca.test.domain.entity.account;

import org.junit.Assert;
import org.junit.Test;

import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.test.domain.AbstractUnitTests;

/**
 * 
 * @author rodrigo.p.fraga@gmail.com
 * @since 09/05/2013
 * @version 1.0
 */
public class UserTests extends AbstractUnitTests
{
	/*-------------------------------------------------------------------
	 *                           ATTRIBUTES
	 *-------------------------------------------------------------------*/

	/*-------------------------------------------------------------------
	 *                           TESTS
	 *-------------------------------------------------------------------*/
	/**
     * 
     */
	@Test
	public void getAuthoritiesMustPass()
	{
		final User user = new User();
		user.setRole( UserRole.ADMINISTRATOR );
		
		Assert.assertNotNull( user.getAuthorities() );
		Assert.assertTrue( user.getAuthorities().contains( UserRole.ADMINISTRATOR ) );
		Assert.assertTrue( user.getAuthorities().contains( UserRole.COLABORADOR_EXTERNO ) );
		Assert.assertTrue( user.getAuthorities().contains( UserRole.ATENDENTE ) );
		Assert.assertTrue( user.getAuthorities().contains( UserRole.OPERADOR_ADMINISNTRATIVO ) );
		Assert.assertTrue( user.getAuthorities().contains( UserRole.OPERADOR_ATENDIMENTOS ) );
		Assert.assertTrue( user.getAuthorities().contains( UserRole.CHEFE_ADMINISTRACAO ) );
	}
}
