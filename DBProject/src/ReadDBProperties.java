import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

//************************************************************************************
// ReadProperties.java		Created By: Larry Gaitan-Rodriguez	Date: 11/24/2020
//																Edited: 11/30/2020
//
// Used to import data from a properties file and return the data using getter methods.
//************************************************************************************

public class ReadDBProperties {
	File configFile;
	
	public ReadDBProperties(File file)
	{
		configFile = file;
	}
	
	
	//Creates the properties Object to read from the dbConfig
	private  Properties dbProp = new Properties();
	
	//Pulls the configuration data from the properties file.
	private void pullDBConfigData() {
		try
		{
			FileInputStream fileInput = new FileInputStream(configFile);
			dbProp.load(fileInput);
			fileInput.close();
		}
		catch(FileNotFoundException fileE)
		{
			fileE.printStackTrace();
		}
		catch(IOException IOE)
		{
			IOE.printStackTrace();
		}
		
	}
	
	
	//Following methods return property values
	public String getURL()
	{
		pullDBConfigData();
		return dbProp.getProperty("url");
	}
	public String getUsername()
	{
		pullDBConfigData();
		return dbProp.getProperty("username");
	}
	public String getPassword()
	{
		pullDBConfigData();
		return dbProp.getProperty("password");
	}
}
