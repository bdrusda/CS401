// CS 0401 Fall 2016
// MySBuilder class.  You must implement this class for Assignment 4.  A shell of
// the class is already provided -- you must fill in the method bodies.

// Note 1: All code for these methods must be your own!  Do not copy these methods
// from code on the internet.  If you do so it will be a violation of the student
// academic integrity code!

// Note 2: You may NOT use StringBuilder or StringBuffer or any similar class in
// any of these methods!  You also may not use String for anything other than the
// argument and return object types (when needed) - i.e. you may not create String 
// objects in order to use String methods to perform any of the methods here.

// Note 3: Some of the methods have additional requirements / restrictions.  Read
// the comments for each method carefully before implementing it.
import java.util.ArrayList;

public class MySBuilder extends SimpleSBuilder
{
	// Data is inherited
	// See SimpleSBuilder for inherited methods
	
	// Redefining inherited constructors to get correct type
	public MySBuilder(int capacity)
	{
		super(capacity);
	}
	
	public MySBuilder(String str)
	{
		super(str);
	}
	
	public MySBuilder(char [] str)													//CREATE NEW SBUILDER OBJECT USING A CHARACTER ARRAY
	{	
		data = new char[2 * str.length];												//create new array to hold the sbuilder object
		for (int i = 0; i < str.length; i++)											//for each space of the array
		{
			data[i] = str[i];															//copy each element of the array
		}
		len = str.length;																//set length variable equal to that of the character array
	}
	
	public MySBuilder(MySBuilder old)												//COPY OLD SBUILDER OBJECT INTO A NEW ONE
	{
		data = new char[2 * old.length()];												//create new array to hold the sbuilder object
		for (int i = 0; i < old.length(); i++)											//for each space of the old sbuilder
		{
			data[i] = old.charAt(i);													//deep copy the info into the new sbuilder
		}
		len = old.length();																//set the length equal to that of the old array
	}
	
	boolean added = false;																//boolean value that is used to determine if the parameter has been added to the sbuilder yet
	
	public MySBuilder append(String str)											//APPEND A STRING TO THE SBUILDER
	{
		added = false;																	//the value has not yet been added by default
		while(!added)																	//while the information has not yet been added
		{
			if(len+str.length() <= super.capacity())									//if the old array + the additional string is less than the physical size of the array
			{
				for(int i = 0; i < str.length(); i++)									//for each character of the string to be appended 
					data[len+i] = str.charAt(i);										//add the character to the next spot in the array
				
				len+=str.length();														//resize the abstract size of the array to account for the appended string
				added = true;
			}
			else																		//if the array is not big enough
				data = resize(data, (len+str.length()));								//resize it 
		}
		
		return this;																	//return the SBuilder
	}
	
	public MySBuilder append(MySBuilder S)											//APPEND A SBUILDER TO THE SBUILDER
	{
		added = false;																	//the value has not yet been added by default
		while(!added)																	//while the value has not yet been added
		{
			if(len+S.length() <= super.capacity())										//if the old array + the sbuilder is less than the physical size of the array
			{
				for(int i = 0; i < S.length(); i++)										//for each character of the string builder to be appended
					data[len+i] = S.charAt(i);											//add the character to the next spot in the array
					
				len+=S.length();														//resize the abstract size of the array to account for the appended string builder
				added = true;															//the SBuilder has been added	
			}
			else																		//if it is not	
				data = resize(data, len+S.length());									//resize the array
		}
		
		return this;																	//return the SBuilder
	}
	
	public MySBuilder append(char [] str)											//APPEND A CHARACTER ARRAY TO THE SBUILDER
	{
		added = false;																	//the value has not yet been added by default
		while(!added)																	//while the value has not yet been added
		{
			if(len+str.length <= super.capacity())										//if the SBuilder + the new array is less than the physical size of the array
			{
				for(int i = 0; i < str.length; i++)										//for each character of the array to be appended
					data[len+i] = str[i];												//add the character to the next spot in the array
					
				len+=str.length;														//resize the abstract size of the array to account for the appended character array
				added = true;															//the character array has been added
			}
			else																		//if it is not	
				data = resize(data, len+str.length);									//resize the array
		}
		
		return this;																	//return the SBuilder																	
	}
	
	public MySBuilder append(char c)												//APPEND A CHARACTER TO THE SBUILDER
	{
		added = false;																	//the value has not yet been added by default
		while(!added)																	//while the value has not yet been added
		{
			if(len+1 <= data.length)													//if the old array + the character is less than the physical size of the array
			{
				data[len] = c;														//add the character to the end of the array
				
				len++;																	//resize the abstract size of the array to account for the appended character array
				added = true;															//the character has been added
			}
			else																		//if it is not	
				data = resize(data, len+1);												//resize the array
		}
		
		return this;																	//return the SBuilder	
	}
	
	public MySBuilder delete(int start, int end)									//DELETE A SEGMENT OF THE SBUILDER
	{
		if(!(start>end || start<0))														//verify that the start and end parameters are valid
		{
			for(int i=0; i<len-1; i++)													//for each value in the SBuilder
			{
				if(end+i < len)															//if the end of the array has not been reached
					data[start+i] = data[end+i];										//shift the characters to the right of the end to the start position	
			}
				
			if(end > len)																//if the end point is greater than that of the array
				end = len;																//simply set it to stop at the end of the array
				
			len-=(end-start);															//leave the remaining characters at the end of the array, but decrement the abstract size, effectively deleting them
		}
		
		return this;																	//return the SBuilder	
	}
	
	public MySBuilder deleteCharAt(int index)										//DELETE A CHARACTER IN THE SBUILDER
	{
		if(!(index < 0 || index > len))													//verify that the index is inside the array
		{
			for(int i=0; i+index+1 < len; i++)											//for each value to the right of the index
				data[i+index] = data[i+index+1];										//shift one to the left
				
			len--;																		//decrement length to account for the removed character
		}
		
		return this;																	//return the SBuilder	
	}
	
	public int indexOf(String str)													//FIND THE POSITION OF A STRING IN THE SBUILDER
	{
		int numMatches = 0;																//if this integer gets to be equal to the length of the string inputted, the substring matches
		int theIndex = 0;																//variable to store the index of the first character in the string
		boolean match = false;															//boolean to determine if a match has been found
		for(int i=0; i<data.length && numMatches!=str.length(); i++)					//check every part of the character array
		{
			match = false;																//not match found by default
			for(int j=0; j+numMatches<str.length() && numMatches!=str.length() && !match; j++)//check every part of the string
			{			
				if(data[i] == str.charAt(j+numMatches))									//if instance i of the array matches instance j of the string
				{
					numMatches++;														//add one to the number of match
					if(numMatches == 1)													//if it is the first match
						theIndex = i;													//set the index equal to it
					match = true;														//there has been a match, break out of the loop
				}
				else																	//if the instances do not match up
				{
					numMatches = 0;														//reset the counter to 0
				}
			}
		}
		
		if(numMatches != str.length())													//if there are not enough matches consecutively to match the string
			return -1;																	//return -1
		
		return theIndex;																//return the start index of the string
	}

	public int indexOf(String str, int fromIndex)									//FIND THE POSITION OF A STRING IN THE SBUILDER STARTING AT A GIVEN POSITION
	{
		int numMatches = 0;																//if this integer gets to be equal to the length of the string inputted, the substring matches
		int theIndex = 0;																//variable to store the index of the first character in the string
		boolean match = false;															//boolean to determine if a match has been found
		for(int i=fromIndex; i<data.length && numMatches!=str.length(); i++)			//check every part of the character array starting with the given index
		{
			match = false;																//not match found by default
			for(int j=0; j<str.length() && numMatches!=str.length() && !match; j++)		//check every part of the string
			{			
				if(data[i] == str.charAt(j+numMatches))									//if instance i of the array matches instance j of the string
				{
					numMatches++;														//add one to the number of matches
					if(numMatches == 1)													//if this is the first instance match
						theIndex = i;													//save the index of the first character
					match = true;														//there has been a match, break out of the loop
				}
				else																	//if the instances do not match up
				{
					numMatches = 0;														//reset the counter to 0
				}
			}
		}
		
		if(numMatches != str.length())													//if there are not enough matches consecutively to match the string
			return -1;																	//return -1
		
		return theIndex;																//return the start index of the string
	}

	public MySBuilder insert(int offset, String str)								//INSERT A STRING INTO THE SBUILDER
	{
		if(!(offset < 0 || offset > len))
		{
			added = false;																//the value has not yet been added by default
			while(!added)																//while the information has not yet been added
			{
				if(len+str.length() <= super.capacity())								//if the old array + the additional string is less than the physical size of the array
				{
					for(int i = 0; i <= len-offset; i++)								//for each character of the string to be added 
						data[len-i+str.length()] = data[len-i];							//shift the characters to the right
					
					for(int j = 0; j<str.length(); j++)									//for each opened up spot in the array
						data[offset+j] = str.charAt(j);									//add the string in the opened up spaces
					
					len+=str.length();													//resize the abstract size of the array to account for the inserted string
					added = true;														//the string has been added
				}
				else																	//if the array is not big enough
					data = resize(data, len+str.length());								//resize it 
			}
		}
		
		return this;																	//return the SBuilder	
	}

	public MySBuilder insert(int offset, char [] str)								//INSERT A CHARACTER ARRAY INTO THE SBUILDER
	{
		added = false;																	//the value has not yet been added by default
		while(!added)																	//while the information has not yet been added
		{
			if(len+str.length <= super.capacity())										//if the old array + the new array is less than the physical size of the array
			{
				for(int i = 0; i <= len-offset; i++)									//for each value to be shifted
					data[len-i+str.length] = data[len-i];								//shift the characters to the end of the array

				for(int j = 0; j<=str.length; j++)										//for each opened up spot in the array
					data[offset+j] = str[j];											//add the string in the opened up spaces
					
				len+=str.length;														//resize the abstract size of the array to account for the inserted character array
				added = true;															//the character array has been added to the character array
			}
			else																		//if the array is not big enough														
				data = resize(data, len+str.length);									//resize it 
		}
		
		return this;																	//return the SBuilder	
	}
	
	public MySBuilder insert(int offset, char c)									//INSERT A CHARACTER INTO THE SBUILDER
	{
		added = false;																	//the value has not yet been added by default
		while(!added)																	//while the information has not yet been added
		{
			if(len+1 <= super.capacity())												//if the old array + the character is less than the physical size of the array
			{
				for(int i = 0; i <= len-offset; i++)									//for each value to be shifted				
					data[len-i] = data[len-i-1];										//shift the characters to the end of the array
					
				data[offset] = c;														//add the character to the end of the array
				
				len++;																	//resize the abstract size of the array to account for the inserted character
				added = true;															//the character has been added to the array													
			}
			else																		//if the array is not big enough
				data = resize(data, len+1);												//resize it 
		}
		
		return this;																	//return the SBuilder	
	}
	
	public MySBuilder insert(int offset, MySBuilder S)								//INSERT A SBUILDER INTO THE SBUILDER
	{
		added = false;																	//the value has not yet been added by default
		while(!added)																	//while the information has not yet been added
		{
			if(len+S.length() <= super.capacity())										//if the old array + the new array is less than the physical size of the array
			{
				for(int i = 0; i <= len-offset; i++)									//for each value to be shifted		
					data[len-i+S.length()] = data[len-i];								//shift the characters to the end of the array

				for(int j = 0; j<=S.length(); j++)										//for each opened up spot in the array
					data[offset+j] = S.charAt(j);										//add the SBuilder in the opened up spaces
					
				len+=S.length();														//resize the abstract size of the array to account for the appended SBuilder
				added = true;																
			}
			else																		//if the array is not big enough
				data = resize(data, len+S.length());									//resize it 
		}
		
		return this;																	//return the SBuilder	
	}

	public MySBuilder replace(int start, int end, String str)						//REPLACE A SEGMENT OF CHARACTERS IN THE SBUILDER WITH A STRING
	{
		if(!(start>end || start<0))									//verify that the start and end parameters are valid
		{
			int replaced = 0;
			int shifted = 0;
			
			for(int i = 0; i<len+str.length()-end; i++)									//for each value to be shifted
			{
				if(str.length() < (end-start))
				{
					if(i<str.length())													//for the length of the string
					{
						data[start+i] = str.charAt(i);									//replace the characters
						replaced++;														//note how many have been replaced
					}
					else																//when the string has been added
					{
						data[start+i] = data[end+i-replaced];							//shift the remaining characters to the left	
					}
				}
				else
				{
					if(i<end)		//first shift the characters to the right	//(i<str.length()-(str.length()-end))
					{
						data[len-i+str.length()-(end-start)-1] = data[len-i-1];			//move everything to the right to make enough room for the string, this is definitely correct.
						shifted++;														//shift the characters to the end of the array
					}
					else		//then fill in the string
					{
						data[start+i-shifted] = str.charAt(i-shifted);
					}
				}
			}
		}		
				
		if(end > len)																	//if the end goes past the length of the array
			end = len;																	//simply set the end to be equal to the length of the array
			
		len-=(end-start);																//subtract the length of the characters to be removed
		len+=str.length();																//add the length of the string
		
		return this;																	//return the SBuilder	
	}
	
	public String substring(int start, int end)										//FIND A STRING IN A SEGMENT OF THE CODE
	{
		char [] theSub = new char [end-start];
		if(!(start > end || start < 0 || end > len))
		{
			for(int i = 0; i<(end-start); i++)											//for each value to be shifted
				theSub[i] = data[start+i];												//shift the characters to the end of the array

			return new String(theSub);
		}
		else
			return null;
	}

	public String [] split(char delim)												//SPLIT THE SBUILDER INTO INDIVIDUAL STRINGS USING A DELIMITER
	{
		ArrayList<String> temp = new ArrayList<String>();
		//List<char> tempString = new ArrayList<char>();
		MySBuilder tempString = new MySBuilder(10);
		for(int i = 0; i < len; i++)													
		{
			if(data[i] == delim)															//if the delimiter is found
			{
				if(tempString.length() > 0)													//and character array has characters in it
				{
					temp.add(tempString.toString());										//add the character array to the stirng array
					tempString.delete(0,tempString.length());								//clear the temp stringBuilder
					
				}
			}
			else																			//if it is not
			{
				tempString.append(data[i]);													//add the character to the character array
			}
		}
		
		if(tempString.length() > 0)															//if the stringBuilder contains anything when done with the string
			temp.add(tempString.toString());												//add it to the string array
		
		String [] delimmed = new String [temp.size()];										//create string array to return
		for(int j = 0; j < delimmed.length; j++)											//for each string in the string arrayList
			delimmed[j] = temp.get(j);														//add the strings to the string array
			
		return delimmed;																	//return the string array
	}
	
	public char [] resize(char [] oldArr, int newSize)									//RESIZE THE ARRAY
	{																						
		char [] newArr = new char[newSize*2];												//create new array 2 times the original's size
		for(int i = 0; i < oldArr.length; i++)												//for each value of the old array
			newArr[i] = oldArr[i];															//copy its value into the new array
			
		return newArr;																		//return the new array
	}
}
	