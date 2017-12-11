// CS 0401 Fall 2016
// Lingo and LingoServer test program
// Your Lingo and LingoServer classes should run with this program without
// any alterations to it.  The output should be identical to that shown in file
// LT-out.txt with the exception of the order of the Lingo objects retrieved from
// your LingoTest object.  Since that must be random, your results should not match
// mine.

// See the Assignment 3 specification sheet for details on the methods in the
// Lingo and LingoServer classes.

import java.util.*;
public class LingoTest
{
	public static void main(String [] args)
	{
		LingoServer LS = new LingoServer("words5.txt");
		System.out.println(LS.toString());
		
		for (int curr = 0; curr < 5; curr++)
		{
			Lingo L = LS.getLingo();
			char f = L.first();
			System.out.println("The word begins with " + f);
			System.out.println(L.toString());
			System.out.println();
		}
		System.out.println();
		
		LS = new LingoServer("words5.txt");
		System.out.println(LS.toString());
		
		for (int curr = 0; curr < 5; curr++)
		{
			Lingo L = LS.getLingo();
			char f = L.first();
			System.out.println("The word begins with " + f);
			System.out.println(L.toString());
			System.out.println();
		}
		System.out.println();
		
		LS = new LingoServer("small5.txt");
		System.out.println(LS.toString());
		Lingo L;
		while (LS.hasLingo())
		{
			L = LS.getLingo();
			System.out.println(L);
		}
		System.out.println(LS);
		System.out.println();
		
		L = LS.getLingo();
		if (L == null)
			System.out.println("No more Lingos left to serve!");
		System.out.println();
		
		L = new Lingo("HELLO");
		System.out.println(L.toString());
		System.out.println();
		
		String word = "HANKY";
		System.out.println("Guess: " + word);
		int [] result = L.guessWord(word);
		showResult(result);	
		System.out.println();
		
		word = "HOTEL";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();
		
		word = "HOOTS";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();
		
		word = "LHOEL";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();

		word = "LLXXX";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();
		
		word = "LXXLX";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();

		word = "LLLLL";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();

		word = "XXXXX";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();

		word = "WEASELHELLO";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();

		word = "LOH";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();

		word = "HELLO";
		System.out.println("Guess: " + word);
		result = L.guessWord(word);
		showResult(result);
		System.out.println();
	}
	
	public static void showResult(int [] A)
	{
		System.out.println("Here are your results: ");
		System.out.println("0 1 2 3 4");
		System.out.println("---------");
		for (int i = 0; i < A.length; i++)
		{
			System.out.print(A[i] + " ");
		}
		System.out.println();
	}
}