package com.digows.blank.test.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;

import com.digows.blank.test.domain.AbstractIntegrationTests;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.redeapoio.RedeApoio;
import com.lucas.graca.domain.service.AccountService;

/**
 * 
 */
public class AccountServiceIntegrationTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Autowired
	private AccountService accountService;
	
	/*-------------------------------------------------------------------
	 *				 		      TESTS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void insertUserMustFail() 
	{
		this.accountService.insertUser( new User() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
	})
	public void insertUserMustPass()
	{
		User user = new User( null, "Testing user", "test@user.com", true, UserRole.OPERADOR_ADMINISNTRATIVO, "user" );
		user = this.accountService.insertUser( user );

		Assert.assertNotNull( user );
		Assert.assertNotNull( user.getId() );
		Assert.assertNotNull( user.getCreated() );
		Assert.assertTrue( user.getEnabled() );
		Assert.assertFalse( user.getPassword().equals( "user" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/redeapoio/redeApoioDataSet.xml",	
		"/dataset/account/UserDataSet.xml",
	})
	public void insertExternalUserMustPass()
	{
		User user = new User( null, "Testing user", "test@user.com", true, UserRole.COLABORADOR_EXTERNO, "user" );
		user.setRedeApoio( new RedeApoio(9999L) );
		user = this.accountService.insertUser( user );

		Assert.assertNotNull( user );
		Assert.assertNotNull( user.getId() );
		Assert.assertNotNull( user.getCreated() );
		Assert.assertTrue( user.getEnabled() );
		Assert.assertFalse( user.getPassword().equals( "user" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/account/UserDataSet.xml",
    })
	public void findUserByIdMustPass()
	{
		final User user = this.accountService.findUserById( 9999L );
	
		Assert.assertNotNull( user );
		Assert.assertNotNull( user.getId() );
		Assert.assertNotNull( user.getCreated() );
		Assert.assertEquals( "admin@email.com", user.getEmail() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/account/UserDataSet.xml",
	})
	public void listUsersByFiltersMustReturn2()
	{
		final Page<User> users = this.accountService.listUsersByFilters( "user", null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 2, users.getTotalElements() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/account/UserDataSet.xml",
	})
	public void listUsersByFiltersMustReturn3()
	{
		final Page<User> users = this.accountService.listUsersByFilters( "1000,1001,xó", null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 3, users.getTotalElements());
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/account/UserDataSet.xml",
	})
	public void listUsersByFiltersMustReturnAll()
	{
		final Page<User> users = this.accountService.listUsersByFilters( null, null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 4, users.getTotalElements() );
	}
}