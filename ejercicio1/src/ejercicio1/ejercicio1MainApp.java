package ejercicio1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ejercicio1MainApp {
	static Connection conexion;

	public static void main(String[] args) {
		
		MySQLConnection("root","password","");
		crateDB("tienda_informatica");
		createTableFab("tienda_informatica", "fabricantes");
		createTableArt("tienda_informatica", "articulos");
		
		insertDataFab("tienda_informatica", "fabricantes", 1, "'Pepe'");
		insertDataArt("tienda_informatica", "articulos", 1, "'Ordenador'", 500, 1);
		
	}
	
	public static void closeConnection() {
		try {
			conexion.close();
			System.out.println("Se ha finalizado la conexion con el servidor");
			
		}catch(SQLException ex){
			System.out.println("falla al cerrar conexion");
		}
	}
	
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
	//crear tabla fabricante
	public static void createTableFab(String db, String tablename) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "CREATE TABLE "+ tablename+
					" (Codigo INT PRIMARY KEY, Nombre VARCHAR(50));";
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("la tabla se ha creado con exito");
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error al crear la tabla");
		}
	}
	//crear tabla articulo
	public static void createTableArt(String db, String tablename) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "CREATE TABLE "+ tablename+
					" (Codigo INT PRIMARY KEY, Nombre VARCHAR(50), Precio int, Fabricante int, FOREIGN KEY(Fabricante) REFERENCES fabricantes(Codigo) ON DELETE CASCADE ON UPDATE CASCADE);";
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("la tabla se ha creado con exito");
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error al crear la tabla");
		}
	}
	//insertar datos en fabrica
	public static void insertDataFab(String db, String tablename, int codigo, String nombre) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "INSERT INTO "+tablename+" (Codigo, Nombre) VALUE("+codigo+", "+nombre+" );";
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("datos almacenados correctamente");
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error en el almacenamiento");
		}
		
	}
	//insertar datos en articulos
	public static void insertDataArt(String db, String tablename, int codigo, String nombre, int precio, int fabricante) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "INSERT INTO "+tablename+" (Codigo, Nombre, Precio, Fabricante) VALUE("+codigo+", "+nombre+", "+precio+", "+fabricante+" );";
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("datos almacenados correctamente");
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error en el almacenamiento");
		}
		
	}
	

}
