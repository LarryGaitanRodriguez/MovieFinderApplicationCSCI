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
			System.out.println("");
			System.out.println("");

			System.out.println("What would you like to do?.");
			System.out.println("1: Look up an actor.");
			System.out.println("2: Look up a movie using a keyword.");
			System.out.println("3: Look up top 10 actors.");
			System.out.println("4: Look up top # actors.");
			System.out.println("5: Look up actors who have performed in the most movies together");
			System.out.println("6: show all actors in the database.");
			System.out.println("7: Show all movies in the database.");
			System.out.println("7: Show all top 10% of movies");
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
				case 5: 
				{
					System.out.println("All actors who have preformed in the most movies togehter");
					DBCommands.workedTogetherActors();				
					break;
				}
				case 6: 
				{
					System.out.println("All actors in the database");
					DBCommands.allActors();
					break;
				}
				case 7: 
				{
					System.out.println("All movies in the database");
					DBCommands.allMovies();
					break;
				}
				case 8:
				{
					System.out.println("Top 10% movies: ");
					DBCommands.top10PercentMovies();
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
		} 

	}
}
//Thank you very much to all the team for their hard work on this project. - Larry

