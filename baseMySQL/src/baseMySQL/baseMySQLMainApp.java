package baseMySQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;



public class baseMySQLMainApp {
	static Connection conexion;

	public static void main(String[] args) {
		
		MySQLConnection("root","password","");
		crateDB("prueba");
		
	}
	//poder cerrar la conexion
	public static void closeConnection() {
		try {
			conexion.close();
			System.out.println("Se ha finalizado la conexion con el servidor");
			
		}catch(SQLException ex){
			System.out.println("falla al cerrar conexion");
		}
	}
	//crear una DB
	public static void crateDB(String name) {
		try {
			String Query ="CREATE DATABASE "+name;
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			closeConnection();
			MySQLConnection("root", "password", name);
			JOptionPane.showMessageDialog(null, "se ha creado la base de datos "+name+" de forma exitosa");
		}catch(SQLException ex) {
			System.out.println("falla al crear");
		}
		
	}
	//crear la conexion con MySQLWorkbench y Docker
	public static void MySQLConnection(String user, String password, String name) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion=DriverManager.getConnection("jdbc:mysql://localhost:33060?useTimezone=true&serverTimezone=UTC",user,password);
			System.out.println("Server Conected");
			
		}catch(SQLException | ClassNotFoundException ex){
			System.out.println("No se ha podido conectar con mi base de datos");
			System.out.println(ex);
		}
	}
	//crear una tabla				//le pasamos el nombre de la base de datos y el nombre de la tabla
	public static void createTable(String db, String tablename, String cratabla) { 
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			//tabla de ejemplo
			String Query = "CREATE TABLE "+ tablename+cratabla;
			
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("la tabla"+tablename+" se ha creado con exito");
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error al crear la tabla "+tablename);
		}
	}
	//insert a una tabla		//le pasamos el nombre de la base de datos el nombre de la tabla y los parametros que necesitamos
	public static void insertData(String db, String tablename, String creainsert) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			//insert de ejemplo
			String Query = "INSERT INTO "+tablename+creainsert;
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("datos almacenados en "+tablename+ " correctamente");
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error en el almacenamiento "+tablename);
		}
		
	}

}