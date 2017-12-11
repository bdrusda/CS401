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

public class Lingo																		//Class to generate individual Lingo objects
{
	String answer;																			//string answer of Lingo
	char [] answerArr = new char[5];														//array of characters formed from the answer
	char [] guessArr = new char[5];															//array of characters formed from the user's guess
	char [] getGuessArr = new char[5];
	
	public Lingo(String word)															//CONVERT GIVEN WORD INTO LINGO OBJECT
	{
		if(word != null)																	//if there is a word
		{
			answer = word.toLowerCase();																	//store the word in answer
			for(int i = 0; i<5; i++)														//assign each letter of the answer
			{																				
					answerArr[i] = answer.charAt(i);										//to a slot of the answer array
			}	
		}
	}
		
	char first()																		//RETURN THE FIRST LETTER OF THE WORD
	{
		char firstChar = 0;																	//char to hold the first character of the word
		try																					//attempt to take
		{
			firstChar = answer.charAt(0);													//the first character of the word
		}
		catch(NullPointerException e)														//if there is an error
		{
			System.out.println("Error in getting first character of the word");				//report it
		}
		return firstChar;																	//return the first letter of the answer
	}
	
	public String toString()															//RETURN THE ANSWER WORD
	{
		return ("Word: "+answer);																		//return the string that contains the answer
	}		
	
	int[] guessWord(String guess)														//INTERPRET THE GUESS WORD AND DETERMINE IF IT HAS ANY SIMILARITY TO THE ANSWER
	{
		String theGuess = guess.toLowerCase();
		for(int i = 0; i<5; i++)															//regenerate the answer array for each guess
		{
			answerArr[i] = answer.charAt(i);	
		}
		
		int[] guessWord = new int[5];														//create array of integers representing correctly guessed letters (0-2), initialized automatically to 0
		for(int i = 0; (i<5)&&(i<guess.length()); i++)										//convert the guess into an array of characters
		{
			try																			
			{
				guessArr[i] = theGuess.charAt(i);												//set each slot of the array equal to one letter of the guess
				getGuessArr[i] = theGuess.charAt(i);
			}	
			catch(NullPointerException n)
			{
				System.out.println("Error in converting the guess to a character array");
			}
		}
		
		for(int i = 0; i<answerArr.length; i++)												//Get the perfect matches
		{
			if(guessArr[i] == answerArr[i])													//if the letter is correct and it's in the right position
			{
				guessWord[i] = 2;															//set that slot of the array to 2
				guessArr[i] = (char) 255;													//remove the letter of the guess from contention after it is used once
				answerArr[i] = (char) 254;													//remove the letter of the answer from contention after it is used once
			}
		}
		
		for(int i = 0; (i<5)&&(i<theGuess.length()); i++)										//get the partial matches
		{
			for(int j = 0; j<5; j++)														 
			{
				if(guessArr[i] ==  answerArr[j])											//if the letter is correct but it is not in the right position
				{
					guessWord[i] = 1;														//set that slot of the array to 1
					guessArr[i] = (char) 255;												//remove the letter of the guess from contention after it is used once
					answerArr[j] = (char) 254;												//remove the letter of the answer from contention after it is used once
				}
			}
		}
		
		return guessWord;																	//return the resulting array
	}
	
	char[] getGuess()
	{
		return getGuessArr;
	}
}