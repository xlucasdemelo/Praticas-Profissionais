package com.digows.blank.test.domain;

import java.util.Locale;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.digows.blank.Application;
import com.digows.blank.test.TestApplication;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import br.com.eits.common.infrastructure.dbunit.DBUnitOperationLookup;
import br.com.eits.common.infrastructure.dbunit.TransactionDbUnitTestExecutionListener;

/**
 * 
 * @author rodrigo.p.fraga@gmail.com
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestApplication.class, Application.class})
@TestExecutionListeners(mergeMode=MergeMode.MERGE_WITH_DEFAULTS, 
	listeners={
		TransactionDbUnitTestExecutionListener.class,
	}
)
@WebIntegrationTest(randomPort=true)
@DatabaseSetup(value="/dataset/AbstractDataSet.xml", type=DatabaseOperation.TRUNCATE_TABLE)
@DbUnitConfiguration( databaseOperationLookup=DBUnitOperationLookup.class, databaseConnection="dbUnitDatabaseDataSourceConnectionFactoryBean")
public abstract class AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
     *                           ATTRIBUTES
     *-------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------
     *                           BEHAVIORS
     *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Before
	public void beforeTest()
	{
		Locale.setDefault( new Locale("pt", "BR") );
	}
}
