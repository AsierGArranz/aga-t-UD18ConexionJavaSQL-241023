package ejercicios3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;



public class ejercicios3AppMain {
	static Connection conexion;

	public static void main(String[] args) {
		
		MySQLConnection("root","password","");
		crateDB("listaalmacenes");
		
		createTableAlm("listaalmacenes", "almacenes");
		insertDataAlm("listaalmacenes", "almacenes", 1, "'c/rambla2'", 200);
		
		createTableCaj("listaalmacenes", "cajas");
		insertDataCaj("listaalmacenes", "cajas", "'357A6'", "'libretas'", 10,1);
		
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
	
	//creamos la tabla almacenes
	public static void createTableAlm(String db, String tablename) { 
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "CREATE TABLE "+ tablename+
					" (CodigoAlm INT PRIMARY KEY, Lugar VARCHAR(50), Capacidad int);";
			
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("la tabla "+tablename+" se ha creado con exito");
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error al crear la tabla "+tablename);
		}
	}
	//creamos la tabla cajas
	public static void createTableCaj(String db, String tablename) { 
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "CREATE TABLE "+ tablename+
					" (NumRef VARCHAR(5) PRIMARY KEY, Contenido VARCHAR(50), Valor int, Almacen int, FOREIGN KEY(Almacen) REFERENCES almacenes(CodigoAlm) ON DELETE CASCADE ON UPDATE CASCADE);";
			
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("la tabla"+tablename+" se ha creado con exito");
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error al crear la tabla "+tablename);
		}
	}
	//insert a la tabla almacenes
	public static void insertDataAlm(String db, String tablename, int codigo, String lugar, int capacidad) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "INSERT INTO "+tablename+" (CodigoAlm, Lugar, Capacidad) VALUE("+codigo+", "+lugar+", "+capacidad+" );";
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("datos almacenados en "+tablename+ " correctamente");
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error en el almacenamiento "+tablename);
		}
		
	}
	
	public static void insertDataCaj(String db, String tablename, String numref, String contenido, int valor, int almacen) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "INSERT INTO "+tablename+" (NumRef, Contenido, Valor, Almacen) VALUE("+numref+", "+contenido+", "+valor+", "+almacen+" );";
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("datos almacenados en "+tablename+ " correctamente");
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error en el almacenamiento "+tablename);
		}
		
	}
	

}