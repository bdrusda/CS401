//Brendan Drusda
//CS 0401
//Wednesday 2PM Lab
//Due 1 November 2016
//Assignment 3 - Lingo

/* Info
First letter of word revealed to user
5 Guesses for each word (for loop)
Each guess
	Correct letter and location - capital letter
	Correct letter - lower case letter
	Incorrect letter - hyphen
	Each letter in word can match one letter of guess at most (if word is hello and guess is hotel only the first L is recorded)
*/

import java.util.*;

public class Assig3
{
	static char[] guessArr = new char[5];																					//array of characters guessed by user
	static int[] key = {2,2,2,2,2}; 																						//key for checking the guess
	static boolean solved;																									//boolean determining if the user has gotten the word right
	
	public static void main (String [] args)
	{
		LingoServer Server;
		Scanner in = new Scanner(System.in);																				//create Scanner object to get user input
		try
		{
			System.out.println("Welcome to Lingo! You are playing with the word list:"+args[0]);							//welcome user and notify them of which list of words was specified in the command line argument
			System.out.println();
			Server = new LingoServer(args[0]);																				//create new Lingo Server
		}
		catch(ArrayIndexOutOfBoundsException a)																				//no file entered
		{
			System.out.println("Welcome to Lingo! You do not appear to have entered a text file so you are playing with the default word list:words5.txt");
			System.out.println();
			Server = new LingoServer("words5.txt");																			//create new Lingo Server
		}
		
		int i = 1;																											//variable to keep track of how many rounds have been played
		boolean quit = false;																								//boolean to determine if the user has quit or not

		while(!quit && Server.hasLingo())																					//while the user has not decided to quit and there are more words
		{
			Lingo L = Server.getLingo();																					//get new word
			guessArr = L.getGuess();																						//create array made from the guess word
			char f = L.first();																								//get the first letter of the answer
			solved = false;																									//boolean determining that the word has not been guessed yet
			System.out.println("Note:\tLetters that are in the word in the correct location are represented as CAPS\n\tLetters that are in the word in the wrong location are represented in lower case\n\tLetters that are not in the word are represented as hyphens");
			System.out.println();																							
			System.out.println("Word "+i+" begins with "+f);																//inform user of what the word begins with
			
			for(int j = 1; j <= 5 && !solved; j++)																			//perform below process 5 times or until the answer is found
			{
				System.out.print("Enter guess number "+j+":");																//prompt user for guess
				String guess = in.nextLine();																				//get guess from user
				
				int [] result = L.guessWord(guess);																			//generate the full guess array
				showResult(result);																							//output the result of the guess
			}
			
			if(solved)																										//if the word has been guessed
				System.out.println("You got it!");																			//alert user that they guessed it
			else																											//if the word has not 
			{
				System.out.println("You didn't quite get it.");																//alert user that they have not guessed it
				System.out.println("The answer was "+L.toString());															//alert the user what the word was
			}
			
			System.out.println("Would you like to play another round? (1 to continue - any other key to quit)");			//ask the user if they'd like to play another round
			if(in.nextLine().equals("1"))																					//if the answer is 1 play again
				quit = false;																								
			else																											//if it is anything else quit
				quit = true;	
			
			i++;																											//add to the counter of rounds played
		}
		
		if(!Server.hasLingo())																								//if the server runs out of words
			System.out.println("Sorry, you are out of words to play.");														//alert the user
		
		System.out.println("Thanks for playing!");																			//goodbye message
	}
	
	public static void showResult(int [] A)																				//SHOW THE USER THE RESULT OF HIS GUESS
	{
		System.out.println("Here are your results: ");																		//give user results of guess
		System.out.println("0 1 2 3 4");																					//slot of each character of guess
		System.out.println("---------");																					
		for (int i = 0; i < A.length; i++)																					//for each slot of the array
		{			
			if(A[i] == 2)																									//if the slot of the array is equal to 2 
			{	
				guessArr[i] = Character.toUpperCase(guessArr[i]);															//make the letter capitalized
			}
			else if(A[i] == 1)																								//if the slot of the array is equal to 1
				guessArr[i] = guessArr[i];																					//leave the letter as is
			else																											//if the slot of the array is 0401
				guessArr[i] = '-';																							//replace the letter with a hyphen
			System.out.print(guessArr[i] + " ");																			//print the converted array
		}
		System.out.println();																								
					
		solved = Arrays.equals(A, key);																						//the answer is solved if the converted array matches the key(2,2,2,2,2)
	}
}