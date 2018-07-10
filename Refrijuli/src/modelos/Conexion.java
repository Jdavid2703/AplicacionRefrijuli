package modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

		private Connection connection;
		private String url = "jdbc:mysql://localhost:3306/refrijuliprincipal";
                private String usuario = "root";
                private String contrasena = "123";
		
		public Connection getConnection() {
			return connection;
		}
		public void setConnection(Connection connection) {
			this.connection = connection;
		}
		
		public void establecerConexion() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(url, usuario, contrasena);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void cerrarConexion() {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

