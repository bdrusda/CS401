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
import java.io.*;

public class LingoServer										//CLASS TO GENERATE A NUMBER OF LINGO OBJECTS
{
	int phys;														//integer that is the physical size of the array
	int abs;														//integer that is a count of how many words are in the array
	int phys2;														//integer that is the physical size of the array of used words
	int abs2;														//set the amount of spaces actually used in the array
	boolean tempUsed;												//boolean that determines if a word has been used or not
	String[] words;													//create reference to array of words
	int[] usedNumbers;												//create reference to array of words that have been used
	String textFile;												//create reference to text file to be scanned
	Scanner getWord;												//create reference to scanner to read text file in
	
	public LingoServer(String fileName)							//CREATES LINGO SERVERs OBJECT
	{
		words = new String[10];										//create array to store words
		usedNumbers = new int[10];									//create array to store used Numbers
		phys = words.length;										//set the physical size of the array
		abs = abs2 = 0;												//set the abstract size of the array
		textFile = fileName;										//store the name of the text file
		File in = new File(textFile);								//create reference to file with words
		
		for(int i = 0; i<usedNumbers.length; i++)					//initialize array of used numbers to -1
			usedNumbers[i] = -1;
		
		try															//attempt to read in the file from the scanner
		{
			getWord = new Scanner(in);								//create scanner to retrieve words from file
		}
		catch (FileNotFoundException f)								//if the file can not be found
		{
			System.out.println("The file could not be found.");		//alert the user
		}
		
		while(getWord.hasNextLine()) 							//WHILE THERE ARE WORDS REMAINING
		{	
			phys = words.length;									//set the physical size of the array
			if(abs < phys)											//if there is space for the new word
			{
				words[abs] = getWord.nextLine().toLowerCase();					//add word from text file to list
				abs++;												//add to the counter of words in the array
			}
			else
				words = resizeString(words);						//if there is not, resize array
		}
	}
	
	boolean hasLingo()											//WHILE THERE ARE STILL WORDS THAT HAVE NOT BEEN USED
	{
		if(abs2 < abs)												//if usedNumbers is less than total numbers
			return true;											//there is a word left, return true
		else														//if usedNumbers is not less than total numbers
			return false;											//there is not a word left, return false
	}

	Lingo getLingo()											//GET THE NEXT WORD
	{
		Random nextLingo = new Random();							//create random number generator
		boolean addWord = false;									//boolean determining if the word has been added
		String theWord = null;										//create reference to string containing the word to be used in new Lingo object
		Lingo newLingo;												//create reference to new Lingo object
		
		if(!hasLingo())												//if there are no more Lingo objects to make
			return null;											//new Lingo object is null
		
		while(!addWord && hasLingo())								//WHILE THE WORD HAS NOT BEEN ADDED YET
		{
			int nextWord = nextLingo.nextInt(abs);					//randomly pick a word in the array
			if(!usedNumbers(nextWord))								//if the word has been used loop through again
			{			
				theWord = words[nextWord];							//add the word to the array
				addWord = true;										//exit loop
			}
		}
			
		newLingo = new Lingo(theWord);								//initialize the new Lingo object to the word chosen by the random generator(or the null value caused by a lack of words)
		
		return newLingo;											//return a Lingo object
	}
	
	public boolean usedNumbers(int used)						//DETERMINE IF THE WORD HAS BEEN USED OR NOT
	{
		int checkNumber = used;										//store the number that is to be checked
		tempUsed = false;											//boolean to be returned determining if the number has been used or not
		phys2 = usedNumbers.length;									//set the physical length of the array

		for(int i = 0; i<abs2; i++)									//ACTAULLY DETERMINING IF THE WORD HAS BEEN USED YET			
		{
			if(checkNumber == usedNumbers[i])						//if the word is in the array
			{
				tempUsed = true;									//return true, the word cannot be used again	
			}
		}
				
		boolean addIt = false;										//create boolean value that determines if the word has been added to the array yet or not
		while(!addIt && !tempUsed)								//WHILE THE WORD HAS NOT BEEN ADDED TO THE LIST AND IT HAS NOT BEEN USED
		{											
			if(abs2 < usedNumbers.length)							//if there is space for the word to be added to the list
			{
				usedNumbers[abs2] = checkNumber;					//add word to end of list
				addIt = true;										//true, the number has been added to the array
				abs2++;												//add to the counter of used words in the array
			}	
			else													//if there is not space for the word to be added to the list
			{
				usedNumbers = resizeInt(usedNumbers);				//resize the array
			}
		}
		
		return tempUsed;											//return the value
	}
		
	public String toString()									//RETURN THE PHYSICAL AND ABSTRACT LENGTH OF THE ARRAY
	{
		String info = ("Lingo Server Info - Number of words:"+abs+"\tCapacity:"+phys);
		return info;												//return string containing array size information
	}
		
	public static String[] resizeString(String[] words)			//MAKE THE ARRAY BIGGER
	{
		int currLength = words.length;								//current length of array
		int newLength = currLength*2;								//desired length of new array
		String[] resized = new String[newLength];					//create reference to new array
			
		for(int i = 0; i<words.length; i++)							//fill new array with values from old
			resized[i] = words[i];
		
		return resized;												//return new array with more space
	}
	
	public static int[] resizeInt(int[] words)					//MAKE THE ARRAY BIGGER
	{
		int currLength = words.length;								//current length of array
		int newLength = currLength*2;								//desired length of new array
		int[] resized = new int[newLength];							//create reference to new array
		
		for(int i = 0; i<words.length; i++)							//fill new array with values from old
			resized[i] = words[i];
			
		return resized;												//return new array with more space
	}
}