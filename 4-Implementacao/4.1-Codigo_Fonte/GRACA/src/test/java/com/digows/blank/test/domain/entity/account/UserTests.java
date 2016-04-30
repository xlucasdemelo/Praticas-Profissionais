package com.digows.blank.test.domain.entity.account;

import org.junit.Assert;
import org.junit.Test;

import com.digows.blank.domain.entity.account.User;
import com.digows.blank.domain.entity.account.UserRole;
import com.digows.blank.test.domain.AbstractUnitTests;

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
		Assert.assertTrue( user.getAuthorities().contains( UserRole.MANAGER ) );
		Assert.assertTrue( user.getAuthorities().contains( UserRole.USER ) );
	}
}
