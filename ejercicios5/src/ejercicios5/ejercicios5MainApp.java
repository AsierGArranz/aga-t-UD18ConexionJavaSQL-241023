package ejercicios5;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;



public class ejercicios5MainApp {
	static Connection conexion;

	public static void main(String[] args) {
		
		MySQLConnection("root","password","");
		crateDB("losdirectores");
		
		createTableDes("losdirectores", "despachos");
		insertDataDes("losdirectores", "despachos", 1, 5);
		
		createTableDir("losdirectores", "directores");
		insertDataDir("losdirectores", "directores", "'39471662M'", "'AsierGonzalez'", "'39471662M'", 1);
		
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
	//crea la tabla despacho
	public static void createTableDes(String db, String tablename) { 
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "CREATE TABLE "+ tablename+
					" (NumeroDes INT PRIMARY KEY, Capacidad int);";
			
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("la tabla"+tablename+" se ha creado con exito");
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error al crear la tabla "+tablename);
		}
	}
	//insert a la tabla despacho
	public static void insertDataDes(String db, String tablename, int numerodes, int capacidad) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "INSERT INTO "+tablename+" (NumeroDes, Capacidad) VALUE("+numerodes+", "+capacidad+" );";
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("datos almacenados en "+tablename+ " correctamente");
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error en el almacenamiento "+tablename);
		}
		
	}
	//crea la tabla directores
	public static void createTableDir(String db, String tablename) { 
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "CREATE TABLE "+ tablename+
					" (Dni VARCHAR(10) PRIMARY KEY, NombreApels VARCHAR(255), DniJefe VARCHAR(10), Despacho int, FOREIGN KEY(DniJefe) REFERENCES directores(Dni) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY(Despacho) REFERENCES despachos(NumeroDes) ON DELETE CASCADE ON UPDATE CASCADE);";
			
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("la tabla"+tablename+" se ha creado con exito");
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error al crear la tabla "+tablename);
		}
	}
	//insert a la tabla directores
	public static void insertDataDir(String db, String tablename, String dni, String nomapel, String dnijefe, int despacho) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "INSERT INTO "+tablename+" (Dni, NombreApels, DniJefe, Despacho) VALUE("+dni+", "+nomapel+", "+dnijefe+", "+despacho+" );";
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("datos almacenados en "+tablename+ " correctamente");
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error en el almacenamiento "+tablename);
		}
		
	}

}