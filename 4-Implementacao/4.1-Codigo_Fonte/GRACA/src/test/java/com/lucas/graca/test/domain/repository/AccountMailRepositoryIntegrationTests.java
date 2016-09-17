package com.lucas.graca.test.domain.repository;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.repository.IAccountMailRepository;
import com.lucas.graca.test.domain.AbstractIntegrationTests;

/**
 * 
 * @author rodrigo.p.fraga@gmail.com
 * @since 09/05/2013
 * @version 1.0
 */

public class AccountMailRepositoryIntegrationTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *                           ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
     * 
     */
	@Autowired
	private IAccountMailRepository accountMailRepository;

	/*-------------------------------------------------------------------
	 *                           TESTS
	 *-------------------------------------------------------------------*/
	/**
	 * @throws ExecutionException 
	 * @throws InterruptedException 
     * 
     */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml"
	})
	@Ignore
	public void sendNewUserAccountTestMustPass() throws InterruptedException, ExecutionException
	{
		final User user = new User();
		user.setEmail( "eits@mailinator.com" );
		user.setName( "Suporte da eits" );

		final Future<Void> emailSent = this.accountMailRepository.sendNewUserAccount( user );
		
		Assert.assertNotNull( emailSent );
		Assert.assertNull( emailSent.get() );
	}
}
