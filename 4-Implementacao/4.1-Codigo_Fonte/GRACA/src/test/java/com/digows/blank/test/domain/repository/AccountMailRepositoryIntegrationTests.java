package com.digows.blank.test.domain.repository;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.digows.blank.test.domain.AbstractIntegrationTests;
import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.repository.IAccountMailRepository;

/**
 * 
 * @author rodrigo.p.fraga@gmail.com
 * @since 09/05/2013
 * @version 1.0
 */
@Ignore
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
