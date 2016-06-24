package hr.fer.zemris.java.tecaj_13.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Ante Spajic
 *
 */
public interface DAO {

	/**
	 * Dohvaća sve postojeće unose u bazi, ali puni samo dva podatka:
	 * id i title.
	 * 
	 * @return listu unosa
	 * @throws DAOException u slučaju pogreške
	 */	
	List<Poll> dohvatiAnkete() throws DAOException;
	
	/**
	 * Dohvaća Bend za zadani id. Ako unos ne postoji, vraća <code>null</code>.
	 * @param id
	 * @return bend iz baze
	 * @throws DAOException
	 */
	List<PollOption> dohvatiOpcije(long pollID) throws DAOException;
	
	/**
	 * Uvećava broj glasova nekog entry-ja u anketi za 1.
	 * 
	 * @param id Id entry-ja kojemu treba uvećati glas
	 * @throws DAOException
	 */
	void azurirajAnketu(long id) throws DAOException;
	
	/**
	 * Dohvaća jednu anketu vezanu na proslijeđeni ID.
	 * 
	 * @param id id ankete
	 * @return Anketa
	 * @throws DAOException
	 */
	Poll dohvatiAnketu(long id) throws DAOException;

	/**
	 * Stvara tablicu sa opcijama za pojedinu anketu.
	 * 
	 * @param cpds
	 * @throws SQLException
	 */
	void stvoriOpcije(DataSource cpds) throws SQLException;

	/**
	 * Stvara tablicu anketa.
	 * 
	 * @param cpds izvor podataka
	 * @throws SQLException
	 */
	void stvoriAnkete(DataSource cpds) throws SQLException;

	boolean isTableEmpty(DataSource cpds, String tableName) throws SQLException;

	/**
	 * Puni tablice sa podacima iz .txt datoteke do koje je potrebno poslati puteljak
	 * 
	 * @param cpds izvor podataka
	 * @param path puteljak do datoteke
	 * @throws IOException
	 * @throws SQLException
	 */
	void napuniTablice(DataSource cpds, String path) throws IOException, SQLException;
}