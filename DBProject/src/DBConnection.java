import java.sql.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Properties;
//************************************************************************************
//DBConection.java		Created By: Larry Gaitan-Rodriguez	Date: 11/24/2020
//
//Connects to the application's database. Makes use of the ReadDBProperties.java file.
//************************************************************************************
import java.util.concurrent.Executor;

//************************************************************************************
//DBConnection.java		Created By: Larry Gaitan-Rodriguez	Date: 11/24/2020
//
//Used to connect to the database. Makes use of ReadDBProperties.java
//************************************************************************************

public class DBConnection {
	
	//Uploading the file with java then passing it over the the property reading object.
	private static File configFile = new File("resources/db.properties");
	private static ReadDBProperties dbConfig = new ReadDBProperties(configFile);
	//Assigns the properties values to global variables
	private static String url = dbConfig.getURL();
	private static String userName = dbConfig.getUsername();
	private static String password = dbConfig.getPassword();
	private static Connection conn;
	
	public static Connection connectToDB(){
		try {
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
            System.out.println("Failed to create the database connection! D:"); 
		}
		return conn;
	}
	

}
