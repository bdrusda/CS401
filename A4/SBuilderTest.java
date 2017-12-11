// CS 0401 Fall 2016.  Assignment 4 Driver program.
// This program must run as is with your MySBuilder class, and produce output
// that exactly matches the output shown in file a4out.txt.

// If you have trouble getting some of the methods to work, you can edit this file
// (ex: comment out the code that does not work).  This will allow you to get partial
// credit for your class.  However, you clearly will not receive credit for the 
// methods that do not work.

public class SBuilderTest
{
	public static void main(String [] args)
	{
		System.out.println("SimpleSBuilder");
		SimpleSBuilder S1, S2, S3;
	
		S1 = new SimpleSBuilder(10);
		S2 = new SimpleSBuilder("Hello!");
		S3 = new SimpleSBuilder("Hello!");
	
		showData(S1);
		showData(S2);
		System.out.println();
		if (S2.equals(S3))
			System.out.println(S2 + " equals " + S3);
		System.out.println();
		
		System.out.println("Testing constructor methods");
		MySBuilder b1 = new MySBuilder("a string");
		char [] c = {' ','a','n','o','t','h','e','r',' ','s','t','r','i','n','g'};
		MySBuilder b2 = new MySBuilder(c);
		MySBuilder b3 = new MySBuilder(10);
		showData(b1);
		showData(b2);
		showData(b3);

		System.out.println("\nTesting Append methods");
		b1.append(b2);  // append a MySBuilder
		showData(b1);
		b1.append(" and another");  // append a String
		showData(b1);
		b1.append(c);   // append a char array
		showData(b1);
		b1.append('!');  b1.append('!');  // append a char
		showData(b1);
		b3.append("...appending data");   // append to empty MySBuilder
		showData(b3);
		
		System.out.println("\nTesting copy constructor");
		MySBuilder b4 = new MySBuilder(b1);
		showData(b4);
		if (b4.equals(b1))
			System.out.println(b4 + "\nequals\n" + b1);
		
		b2 = new MySBuilder(c);  // reinitialize b2
		System.out.println("\nTesting charAt method");
		for (int i = 0; i < b2.length(); i++)
		{
			System.out.print(b2.charAt(i));
		}
		System.out.println();

		System.out.println("\nTesting delete method");
		b1 = new MySBuilder("we build a string of everything");
		System.out.println(b1);
		b1.delete(9,21);	// delete in middle
		System.out.println(b1);
		// Deleting from the front
		b1.delete(0,3);
		System.out.println(b1);
		// Deleting "past" the end just deletes to the end
		b1.delete(5,60);
		System.out.println(b1);
		
		System.out.println("\nTesting deleteCharAt method");
		b1 = new MySBuilder("Xhere is a funney little stringh");
		System.out.println(b1);
		// Delete from the front
		b1.deleteCharAt(0);
		System.out.println(b1);
		// Delete in middle
		b1.deleteCharAt(14);
		System.out.println(b1);
		// Delete at end
		b1.deleteCharAt(b1.length()-1);
		System.out.println(b1);
		// Delete at location past the end does nothing
		b1.deleteCharAt(40);
		System.out.println(b1);
		
		System.out.println("\nTesting indexOf method");
		b1 = new MySBuilder("who is whoing over in whoville");
		String s1 = new String("who");
		String s2 = new String("whoing");
		String s3 = new String("whoville");
		String s4 = new String("whoviller");
		String s5 = new String("whom");
		int i1 = b1.indexOf(s1);
		int i2 = b1.indexOf(s2);
		int i3 = b1.indexOf(s3);
		int i4 = b1.indexOf(s4);
		int i5 = b1.indexOf(s5);
		System.out.println(s1 + " was found at " + i1);
		System.out.println(s2 + " was found at " + i2);
		System.out.println(s3 + " was found at " + i3);
		System.out.println(s4 + " was found at " + i4);
		System.out.println(s5 + " was found at " + i5);
		
		i1 = b1.indexOf(s1, 1);
		i2 = b1.indexOf(s1, 7);
		i3 = b1.indexOf(s1, 8);
		i4 = b1.indexOf(s1, 23);
		System.out.println(s1 + " was found at " + i1);
		System.out.println(s1 + " was found at " + i2);
		System.out.println(s1 + " was found at " + i3);
		System.out.println(s1 + " was found at " + i4);
		
		System.out.println("\nTesting insert and replace methods");
		b1 = new MySBuilder("this is cool");
		b1.insert(8, "very ");
		showData(b1);
		// Insert at the front
		b1.insert(0, "Yes, I think ");
		showData(b1);
		// Insert at the end
		b1.insert(30, " to insert at the end.");
		showData(b1);
		// Insert past the end (does not insert)
		b1.insert(53, " this is not inserted!");
		showData(b1);
		
		// Replace with a shorter string
		b1.replace(13, 30, "it is fun");
		showData(b1);
		// Replace with a longer string
		b1.replace(0, 22, "No, it is definitely bogus");
		showData(b1);
		
		System.out.println("\nTesting substring method");
		b1 = new MySBuilder("work hard to finish this assignment");
		String sub = b1.substring(25, 31);
		System.out.println(sub);
		sub = b1.substring(5,9);
		System.out.println(sub);
		// Substring at front
		sub = b1.substring(0,4);
		System.out.println(sub);
		
		System.out.println("\nTesting MySBuilder return types");
		b1 = new MySBuilder("Hello");
		b2 = new MySBuilder("MySBuilder");
		b1.append(" there ").append(b2).append(' ').append("fans!");
		System.out.println(b1);
		b1.delete(12,22).insert(12,"CS0445").append('!');
		System.out.println(b1);
		
		System.out.println("\nTesting split method");
		b1 = new MySBuilder("This is a neat operation");
		System.out.println(b1.toString());
		String [] ans = b1.split(' ');
		for (String X: ans)
			System.out.println(X);
		System.out.println();
		
		b1 = new MySBuilder("--This--is----kind-of---wacky--!--");
		System.out.println(b1.toString());
		ans = b1.split('-');
		for (String X: ans)
			System.out.println(X);
		System.out.println();
	}
	
	public static void showData(SimpleSBuilder S)
	{
		System.out.print("Data: " + S.toString());
		System.out.print(" Len: " + S.length());
		System.out.println(" Cap: " + S.capacity());
	}
}
