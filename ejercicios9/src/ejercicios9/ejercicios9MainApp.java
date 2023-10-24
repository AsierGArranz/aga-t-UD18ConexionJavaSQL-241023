package ejercicios9;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;



public class ejercicios9MainApp {
	static Connection conexion;

	public static void main(String[] args) {
		
		MySQLConnection("root","password","");
		crateDB("losinvestigadores");
		
		createTable("losinvestigadores", "facultad"," (CodigoFac int PRIMARY KEY, Nombre VARCHAR(255));");
		insertData("losinvestigadores", "facultad", " (CodigoFac, Nombre) VALUE(1,'Ciencias');");
		
		createTable("losinvestigadores", "investigadores", " (Dni VARCHAR(10) PRIMARY KEY, NomApels VARCHAR(255), Facultad int),"
				+ "FOREIGN KEY(Facultad) REFERENCES facultad(CodigoFac) ON DELETE CASCADE ON UPDATE CASCADE);");
		insertData("losinvestigadores", "investigadores", "(Dni, NomApels, Facultad) VALUE('39471662M','Asier Gonzalez',1);");
		
		createTable("losinvestigadores", "equipos", " (NumSerie VARCHAR(5) PRIMARY KEY, Nombre VARCHAR(255) , Facultad int,"
				+ "FOREIGN KEY(Facultad) REFERENCES facultad(CodigoFac) ON DELETE CASCADE ON UPDATE CASCADE)");
		insertData("losinvestigadores", "equipos", "(NumSerie, Nombre, Facultad) VALUE('123A', 'Amarillo',1);");
		
		createTable("losinvestigadores", "reserva", "(Dni VARCHAR(10), NumSerie VARCHAR(5), Comienzo date, Fin date, PRIMARY KEY (Dni, NumSerie),"
				+ "FOREIGN KEY(Dni) REFERENCES investigadores(Dni) ON DELETE CASCADE ON UPDATE CASCADE,"
				+ "FOREIGN KEY(NumSerie) REFERENCES equipos(NumSerie) ON DELETE CASCADE ON UPDATE CASCADE)");
		insertData("losinvestigadores", "reserva", "(Dni, NumSerie, Comienzo, Fin) VALUE('39471662M','123A',2000-01-01, 2001-01-01);");
		
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
	//crear una tabla
	public static void createTable(String db, String tablename, String cratabla) { 
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "CREATE TABLE "+ tablename+ cratabla;
			
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("la tabla"+tablename+" se ha creado con exito");
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("error al crear la tabla "+tablename);
		}
	}
	//insert a una tabla
	public static void insertData(String db, String tablename, String creainsert) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
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