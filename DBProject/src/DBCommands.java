import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
//************************************************************************************
//DBCommands.java		Created By: Larry Gaitan-Rodriguez	Date: 11/24/2020
//
//All the used database commands in a central location. Will be called by methods.
//************************************************************************************
import java.util.Scanner;

public class DBCommands {
	public DBCommands() {}
	
	private static boolean movieCastNuked;
	private static boolean movieNameNuked;
	
	// Used to drop the movie_cast table in the DB
	public static void nukeMovieCast() 
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			String nukeMovieCast = "DROP TABLE movie_cast";
			PreparedStatement tableNuke = connect.prepareStatement(nukeMovieCast);
			tableNuke.executeUpdate();
			System.out.println("movie_cast table did exist. It was nuked.");
			movieCastNuked = true;

		}
		catch(SQLException e)
		{
			System.out.println("movie_cast table doesn't exist");
			movieCastNuked = false;
		}
	}
	
	//Used to drop movie_name_score table.
	public static void nukeMovieName() 
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			String nukeMovieName = "DROP TABLE movie_name_score";
			PreparedStatement tableNuke = connect.prepareStatement(nukeMovieName);
			tableNuke.executeUpdate();
			System.out.println("movie_name_score table did exist. It was nuked.");
			movieNameNuked = true;
		}
		catch(SQLException e)
		{
			System.out.println("movie_name_score table doesn't exist.");
			movieNameNuked = false;
		}
	}
	
	//combined both nuke methods.
	public static void nukeBothTables()
	{
		System.out.println("Attempting to nuking movie_cast and movie_name_score tables.");
		nukeMovieCast();
		nukeMovieName();
	}
	
	
	//The following methods are for creating the required tables for the program.
	public static void createMovieCastTable() 
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			String movieCastTableCreationSQL = "CREATE TABLE movie_cast (movieID INTEGER, castID INTEGER, cname CHAR(255), PRIMARY KEY (movieID, castID),"
					+ "FOREIGN KEY (MovieID) REFERENCES movie_name_score(MovieID));";
			PreparedStatement prepStatement2 = connect.prepareStatement(movieCastTableCreationSQL);
			prepStatement2.executeUpdate(movieCastTableCreationSQL);
			System.out.println("movie_cast table has been created");
		}
		catch(SQLException e)
		{
			System.out.println("movie_cast table already exists.");
			
		}
	}
	
	public static void createMovieNameTable() 
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			String movieNameScoreTableCreationSQL = "CREATE TABLE movie_name_score(movieID Integer, mname CHAR(255), mscore Integer, PRIMARY KEY (MovieID));";
			PreparedStatement prepStatement = connect.prepareStatement(movieNameScoreTableCreationSQL);
			prepStatement.executeUpdate();
			System.out.println("movie_name_score table has been created");
		}
		catch(SQLException e)
		{
			System.out.println("Cmove_name_score table already exists.");
		}
	}
	
	public static void createBothTables()
	{
		createMovieNameTable();
		createMovieCastTable();
	}
	
	
	//Selects and displays all the actors available.
	public static void allActors()
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			PreparedStatement prepStatement = connect.prepareStatement("SELECT * FROM movie_cast");
			ResultSet rs = prepStatement.executeQuery();
			int i = 1;
			while(rs.next())
			{
				System.out.println(i+" "+rs.getString(3));
				i++;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Critical error using actorLookup method.");
			e.printStackTrace();
		}
	}
	
	//Selects and displays all the movies available and the scores
	public static void allMovies()
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			PreparedStatement prepStatement = connect.prepareStatement("SELECT * FROM movie_name_score");
			ResultSet rs = prepStatement.executeQuery();
			int i = 1;
			while(rs.next())
			{
				int movieScore = rs.getInt(3);
				if(movieScore == -1)
					System.out.println(i+" "+rs.getString(2)+" which is not rated.");
				else
					System.out.println(i+" "+rs.getString(2)+"w/ score of : "+movieScore+".");
				i++;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Critical error using actorLookup method.");
			e.printStackTrace();
		}
	}
	
	//Looks up the actors, the movies they're in, and the score.
	public static void actorLookup(String actorName)
	{
		Connection connect = DBConnection.connectToDB();
		try
		{	
			PreparedStatement prepStatement = connect.prepareStatement(
					"SELECT mname, mscore "+
					"FROM movie_name_score "+
					"NATURAL JOIN movie_cast "+
					"WHERE cname = ? "+
					"ORDER BY mscore DESC;");
			prepStatement.setString(1,actorName);
			ResultSet rs = prepStatement.executeQuery();
			
			System.out.println(actorName+" acted in... ");
			while(rs.next())
			{
				
				int movieScore = rs.getInt(2);
				String movieName = rs.getString(1);
				if(movieScore == -1)
				{
					System.out.println("'"+movieName+"'"+" which was unscored.");
				}
				else 
				{
					System.out.println("'"+movieName+"'"+" with a score of "+movieScore);
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println("Critical error using actorLookup method.");
			e.printStackTrace();
		}
	}
	
	//Will search top 10 actors
	public static void topActors()
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			PreparedStatement prepStatement = connect.prepareStatement(
					"SELECT Cname, Mscore "+
					"FROM movie_cast "+
					"NATURAL JOIN movie_name_score "+
					"WHERE Mscore = (SELECT MAX(Mscore) FROM movie_name_score) "+
					"LIMIT 10;");

			ResultSet rs = prepStatement.executeQuery();
			
			System.out.println("Top 10 most popular actors based on scores are...");
			int tally = 1;
			while(rs.next())
			{
				String actorName = rs.getString(1);
				int avgScore = rs.getInt(2);
				
				//Weird edge case which shouldn't be possible but who knows.
				if(avgScore == -1)
				{
					System.out.println("#"+tally+". '"+actorName+"'"+" which has no rated movie.");
				}
				else 
				{
					System.out.println("#"+tally+". '"+actorName+"'"+" with a score of "+avgScore);
				}
				tally++;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Critical error using topActors method.");
			e.printStackTrace();
		}
	}
	
	//Method overload of topActors. User can chose top 5, 50, 500, or even 500 (But thats a bit dumb by then).
	public static void topActors(int amount)
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			PreparedStatement prepStatement = connect.prepareStatement(
					"SELECT Cname, Mscore "+
					"FROM movie_cast "+
					"NATURAL JOIN movie_name_score "+
					"WHERE Mscore = (SELECT MAX(Mscore) FROM movie_name_score) "+
					"LIMIT ?;");
			prepStatement.setInt(1, amount);
			ResultSet rs = prepStatement.executeQuery();
			
			System.out.println("Top "+amount+" most popular actors based on scores are...");
			
			int tally = 1;
			while(rs.next())
			{
				String actorName = rs.getString(1);
				int avgScore = rs.getInt(2);
				
				//Weird edge case which shouldn't be possible but who knows.
				if(avgScore == -1)
				{
					System.out.println("#"+tally+". '"+actorName+"'"+" which has no rated movie.");
				}
				else 
				{
					System.out.println("#"+tally+". '"+actorName+"'"+" with a score of "+avgScore);
				}
				tally++;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Critical error using topActors method.");
			e.printStackTrace();
		}
	}
	public static void workedTogetherActors()
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			PreparedStatement actorTogetherSearch = connect.prepareStatement(
					"SELECT a.cname, b.cname, COUNT(b.movieID) movies_together "+
					"FROM movie_cast a JOIN movie_cast b "+
					"ON (a.movieID = b.movieID) "+
					"WHERE a.cname <> b.cname "+
					"GROUP BY a.cname, b.cname "+
					"ORDER BY MAX(movies_together) DESC "+
					"LIMIT 2");

			ResultSet rs = actorTogetherSearch.executeQuery();
			
			while(rs.next())
			{
				String actorName1 = rs.getString(1);
				String actorName2 = rs.getString(2);
				int moviesTogether = rs.getInt(3);
				
				System.out.println(actorName1+" and "+actorName2+" worked in "+moviesTogether+" movies together.");
			}
		}
		catch(SQLException e)
		{
			System.out.println("Critical error using topActors method.");
			e.printStackTrace();
		}
	}
	//Top 10% of movies 
	public static void top10PercentMovies()
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			PreparedStatement actorTogetherSearch = connect.prepareStatement(
					"SELECT Mname, Mscore "+
					"FROM movie_name_score "+
					"WHERE Mscore BETWEEN 90 AND 100;");

			ResultSet rs = actorTogetherSearch.executeQuery();
			
			int count = 1;
			while(rs.next())
			{
				String movieName = rs.getString(1);
				int movieScore = rs.getInt(2);
				
				System.out.println("#"+count+" "+movieName+" with a score of "+movieScore);
				count++;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Critical error using topActors method.");
			e.printStackTrace();
		}
	}

	
	//Method takes keyword to find a movie with the keyword (only one) in it.
	
	public static void movieKeywordSearch(String keyword)
	{
		Connection connect = DBConnection.connectToDB();
		try
		{
			PreparedStatement prepStatement = connect.prepareStatement(
					"SELECT mname, mscore "+
					"FROM movie_name_score "+
					"WHERE mname LIKE ? "+
					"ORDER BY mname ASC; ");
			prepStatement.setString(1, "%"+keyword+"%");
			ResultSet rs = prepStatement.executeQuery();
			
			System.out.println("Results for the keyword '"+keyword+"' are the following...");
			
			int tally = 1;
			while(rs.next())
			{
				String movieName= rs.getString(1);
				int avgScore = rs.getInt(2);
				
				//If movie is not rated.
				if(avgScore == -1)
				{
					System.out.println("#"+tally+". '"+movieName+"'"+" which is not rated.");
				}
				else 
				{
					System.out.println("#"+tally+". '"+movieName+"'"+" with a score of "+avgScore);
				}
				tally++;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Critical error using keywords method.");
			e.printStackTrace();
		}
	}
	
	
}
