package DAO;

import java.sql.SQLException;

public class DAOFactory {
	public IDAOManager DAOManagerFactory() throws SQLException {
		return new DAOManagerJDBCImpl();
	}
}
