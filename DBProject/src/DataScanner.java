import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

//************************************************************************************
// DataScanner.java		Created By: Larry Gaitan-Rodriguez	Date: 11/24/2020
//
// Used to scan in data from files or into the database. Makes use of DBconnection.java
//************************************************************************************

public class DataScanner {
	private File file;
	public DataScanner(File file) {
		this.file = file;
	}
	public void scanMovieCastAndUpload()
	{
		Connection connect = DBConnection.connectToDB();
		System.out.println("Scanning in all movie casts. May take a moment...");

		try {
			Scanner castReader = new Scanner(new FileInputStream(file));
			
			
			int castCounter = 0;
			while(castReader.hasNextLine())
			{
				String currentLine = castReader.nextLine();
				String[] delimitedLine = currentLine.split(",");
				//Following the format of the movie-cast, first two data points will
				//be integers and the last one will be a name.
				int movieID = Integer.parseInt(delimitedLine[0]);
				int castID = Integer.parseInt(delimitedLine[1]);
				String actorName = delimitedLine[2];
				actorName = actorName.replace("\"", "");
				PreparedStatement postNM = connect.prepareStatement("INSERT INTO movie_cast (movieID, castID, cname) "
						+ "VALUES(?,?,?)");
				postNM.setInt(1, movieID);
				postNM.setInt(2, castID);
				postNM.setString(3, actorName);
				postNM.executeUpdate();
				castCounter++;
			}
			
			castReader.close();
			System.out.println("inserted "+castCounter+" lines into movie_cast");
			
		} catch (FileNotFoundException e) {
			System.out.println("File was not found. Please make sure its in the correct folder or correctly named.");
			e.printStackTrace();
		} catch (SQLException SQLE) {
			System.out.println("SQL Query went wrong!?");
			SQLE.printStackTrace();
		}

	}
	public void ScanNameAndUpload()
	{
		//Will now make use of the scanner class to take in data from file
		try {
			Connection connect = DBConnection.connectToDB();

			Scanner movieNameReader = new Scanner(new FileInputStream(file));
			
			//Goes thru the whole file to process the data and upload it
			int mnCounter = 0;
			while(movieNameReader.hasNextLine())
			{
				String currentLine = movieNameReader.nextLine();
				
				
				//Thank you very much stack overflow: https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
				String[] delimitedLine = currentLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				//Following the format of the movie-cast, first two data points will
				//be integers and the last one will be a name.
				int movieID = Integer.parseInt(delimitedLine[0]);
				String movieName = delimitedLine[1];
				//Removing the quotation marks.
				movieName = movieName.replace("\"", "");
				int score = Integer.parseInt(delimitedLine[2]);
				
				PreparedStatement postNM = connect.prepareStatement("INSERT INTO movie_name_score (movieID, mname, mscore ) "
						+ "VALUES(?,?,?)");
				postNM.setInt(1, movieID);
				postNM.setString(2, movieName);
				postNM.setInt(3, score);
				
				postNM.executeUpdate();
				mnCounter++;
			}
			movieNameReader.close();
			System.out.println("inserted "+mnCounter+" lines into movie_name_cast");
		} catch (FileNotFoundException e) {
			System.out.println("File was not found. Please make sure its in the correct folder or correctly named.");
			e.printStackTrace();
		} catch (SQLException SQLE) {
			System.out.println("SQL Query went wrong!?");
			SQLE.printStackTrace();
		}
	}
	
	public void setFileUpload(File file) 
	{
		this.file = file;
	}
	
	public File getFileUpload()
	{
		return file;
	}

}
