package hr.fer.zemris.java.tecaj_13;

import java.beans.PropertyVetoException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Listener koji sluša na početak aplikacije i prilikom pokretanja aplikacije
 * stvara potrebne podatke da bi aplikacija funkcionirala, spaja se na bazu
 * podataka koja mora već prije postojati, te ako u toj bazi ne postoje potrebne
 * tablice stvara ih i puni podacima s kojima će raditi dalje u aplikaciji.
 * 
 * @author Ante Spajic
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Properties prop = new Properties();
			String p = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
			prop.load(Files.newInputStream(Paths.get(p)));
			String host = prop.getProperty("host");
			String port = prop.getProperty("port");
			String dbName = prop.getProperty("name");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password="
					+ password;
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			} catch (PropertyVetoException e1) {
				throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
			}
			cpds.setJdbcUrl(connectionURL);
			try {
				DAOProvider.getDao().stvoriAnkete(cpds);
				DAOProvider.getDao().stvoriOpcije(cpds);
				if (DAOProvider.getDao().isTableEmpty(cpds, "Polls")) {
					DAOProvider.getDao().napuniTablice(cpds,
							sce.getServletContext().getRealPath("/WEB-INF/ankete-definicija.txt"));
				}
			} catch (Exception e) {
				System.out.println("---Nisam uspio stvoriti tablice---");
				e.printStackTrace();
				System.exit(1);
			}
			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}