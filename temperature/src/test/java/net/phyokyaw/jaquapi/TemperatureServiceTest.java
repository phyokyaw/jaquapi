package net.phyokyaw.jaquapi;

import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.Assert;
import org.junit.Test;

public class TemperatureServiceTest {

	public static class ContextBuilder implements InitialContextFactoryBuilder {
		@Override
		public InitialContextFactory createInitialContextFactory(
				Hashtable<?, ?> environment) throws NamingException {
			return new TestInitialContextFactory();
		}
	}

	@Test
	public void testWriteRead() throws Exception {
		NamingManager.setInitialContextFactoryBuilder(new ContextBuilder());
		InitialContext ic = new InitialContext();
		EmbeddedDataSource ds = new EmbeddedDataSource();
		ds.setDatabaseName("target/TestDB");
		ds.setCreateDatabase("create");
		ic.bind("osgi:service/javax.sql.DataSource/(osgi.jndi.service.name=jdbc/jaquapi-local)", ds);
		AquaTemperatureService temperatureWriteService = new AquaTemperatureService();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jaquapi-local", System.getProperties());
		EntityManager em = emf.createEntityManager();
		temperatureWriteService.setEntityManager(em);
		temperatureWriteService.setTemperatureFilePath(AquaTemperatureService.class.getResource("w1_slave").getFile());
		temperatureWriteService.update();
		//		em.getTransaction().begin();
		//		temperatureWriteService.record();
		//		em.getTransaction().commit();
		//		List<TemperatureRecord> records = temperatureWriteService.getAll();
		//		Assert.assertTrue(records.size() == 1);
		Assert.assertTrue(temperatureWriteService.getValue() == 18.437f);
	}
}
