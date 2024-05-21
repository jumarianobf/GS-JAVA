package br.com.fiap.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection conexao() throws ClassNotFoundException, SQLException {
		
		// Driver 
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		return DriverManager.getConnection
				("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl" ,
						"rm552713" , "300305");
	}

}
