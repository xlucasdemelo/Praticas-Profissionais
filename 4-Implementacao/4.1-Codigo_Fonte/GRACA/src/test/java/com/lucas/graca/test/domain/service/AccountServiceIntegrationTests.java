package com.lucas.graca.test.domain.service;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.redeapoio.RedeApoio;
import com.lucas.graca.domain.service.AccountService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;

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
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
	})
	public void insertUserMustPass()
	{
		User user = new User( null, "Testing user", "test@user.com", null, UserRole.OPERADOR_ADMINISTRATIVO, "user" );
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
	@Test(expected=ValidationException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
	})
	public void insertUserMustFailWithoutMandatoryFields()
	{
		User user = new User( null, null, "test@user.com", true, UserRole.OPERADOR_ADMINISTRATIVO, "user" );
		user = this.accountService.insertUser( user );

		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
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
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",	
		"/dataset/account/UserDataSet.xml",
	})
	public void insertExternalUserMustFailWithoutRedeAPoio()
	{
		User user = new User( null, "Testing user", "test@user.com", true, UserRole.COLABORADOR_EXTERNO, "user" );
		user.setRedeApoio( null );
		
		user = this.accountService.insertUser( user );

		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",	
		"/dataset/account/UserDataSet.xml",
	})
	public void changePasswordMustPass()
	{
		String password = "NovaSenha";
		String confirmacao = "NovaSenha";
		
		this.accountService.changePassword( 9999L, password, confirmacao );
		
		Assert.assertTrue( password.equals( "NovaSenha" ) );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",	
		"/dataset/account/UserDataSet.xml",
	})
	public void changePasswordMustFailSenhaDiferente()
	{
		String password = "NovaSenha123";
		String confirmacao = "NovaSenha";
		
		this.accountService.changePassword( 9999L, password, confirmacao );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",	
		"/dataset/account/UserDataSet.xml",
	})
	public void changePasswordDisabledUserMustFail()
	{
		String password = "NovaSenha";
		String confirmacao = "NovaSenha";
		
		this.accountService.changePassword( 1001L, password, confirmacao );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@admin.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
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
	@WithUserDetails("admin@admin.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
    })
	public void findUserMustReturnExternalUser()
	{
		final User user = this.accountService.findUserById( 1001L );
		
		Assert.assertNotNull( user );
		Assert.assertNotNull( user.getId() );
		Assert.assertNotNull( user.getCreated() );
		Assert.assertTrue( user.getRole() == UserRole.COLABORADOR_EXTERNO );
	}
	
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml"
	})
	public void listByFiltersMustReturnAllEnabled()
	{
		final Page<User> users = this.accountService.listByFilters( null, true, null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 1, users.getTotalElements() );
	}
	
	/**
	 * 
	 */
}