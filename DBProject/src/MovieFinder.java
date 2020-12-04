import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;



public class MovieFinder{

	public static void main(String[] args) 
	{
		String userInput;
		Scanner input = new Scanner(System.in);
		
		//Will ask the user if they want to set up the tables 		
		//Will now go thru user set up...
		while(!cmdLine.isSetUpComplete())
		{
			System.out.println("Is this your first time running the application? Y/N else EXPLAIN ");
			userInput = input.nextLine();
			cmdLine.startUp(userInput);
		}

		System.out.println();
		System.out.println("Welcome to the Movie Finder app! Please enjoy your time here.");
		//Scanner userChoices = new Scanner(System.in);
		
		boolean continueMenu = true;
		while(continueMenu)
		{
		Scanner userChoices = new Scanner(System.in);
			int choice;
			int userCount;
			System.out.println("");
			System.out.println("");

			System.out.println("What would you like to do?.");
			System.out.println("1: Look up an actor.");
			System.out.println("2: Look up a movie using a keyword.");
			System.out.println("3: Look up top 10 actors.");
			System.out.println("4: Look up top # actors.");
			System.out.println("0: Exit");
			choice = userChoices.nextInt();
			switch(choice)
			{
				case 1:
				{
					Scanner scan = new Scanner(System.in);
					System.out.println("Please input actor name");
					String actorName = scan.nextLine();
					System.out.println("actor inputed: "+actorName);
					DBCommands.actorLookup(actorName);
					break;
				}
				
				case 2:
				{
					Scanner scan = new Scanner(System.in);
					System.out.println("Please input a movie keyword");
					String movieKeyword = scan.nextLine();
					DBCommands.movieKeywordSearch(movieKeyword);
					break;
				}
			
				case 3:
				{
					DBCommands.topActors();
					break;
				}
				
				case 4:
				{
					Scanner scan = new Scanner(System.in);
					System.out.println("What top number of actors would you like to see: ");
					int topActorCount = scan.nextInt();
					DBCommands.topActors(topActorCount);
					break;
				}
				
				case 0:
				{
					System.out.println("Thanks for coming.");
					continueMenu = false;
					break;
				}
				default:
					System.out.println("Please enter a valid input");
				
			}
		} // end while 
		
		/*
		//Cleans up the database
		DBCommands.nukeBothTables();
		
		//Now we create the tables.
		DBCommands.createBothTables();
		
		//Files are loaded onto the program and their contents are uploaded to the database
		File MovieNS = new File("movie-name-score.txt");
		DataScanner fileScanner = new DataScanner(MovieNS);
		fileScanner.ScanNameAndUpload();
		File MovieCast = new File("movie-cast.txt");
		fileScanner.setFileUpload(MovieCast);
		fileScanner.scanMovieCastAndUpload();
		
		/
		*/
		/*
		while(true)
		{
			String userInput = input.nextLine();
			DBCommands.actorLookup(userInput);
			DBCommands.topActors();
			DBCommands.topActors(100);
			DBCommands.movieKeywordSearch("death");
		}
		*/
		
		
		
	}
}

