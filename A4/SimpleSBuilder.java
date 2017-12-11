// CS 0401 Fall 2016
// Class SimpleSBuilder is a very primitive class that you will extend
// with your MySBuilder class for Assignment 4
// Note that data and the methods that are already done -- do not reinvent
// the wheel!

public class SimpleSBuilder
{
	protected char [] data;
	protected int len;
	
	// Simple constructors.
	public SimpleSBuilder()
	{
		data = new char[10];
		len = 0;
	}
	
	public SimpleSBuilder(int capacity)
	{
		data = new char[capacity];
		len = 0;
	}
	
	public SimpleSBuilder(String str)
	{
		data = new char[2 * str.length()];
		for (int i = 0; i < str.length(); i++)
		{
			data[i] = str.charAt(i);
		}
		len = str.length();
	}
	
	public char charAt(int index)
	{
		if (index < 0 || index > len)
			throw new IndexOutOfBoundsException();
		return data[index];
	}
		
	public String toString()
	{
		// Note this String constructor.  You may need it in some of your
		// other methods!
		return new String(data, 0, len);
	}
	
	public int capacity()
	{
		return data.length;
	}
	
	public int length()
	{
		return len;
	}
	
	// Unlike standard StringBuilder, we will allow our SimpleSBuilders
	// to be compared for equality!
	public boolean equals(SimpleSBuilder arg)
	{
		if (len != arg.len)
			return false;
		boolean match = true;
		for (int i = 0; i < len; i++)
			if (data[i] != arg.data[i])
			{
				match = false;
				break;
			}
		return match;
	}			
	
	// This method is protected since we don't want the user to call it but
	// we do want to be able to call it from subclasses.
	protected void resize(int newcap)
	{
		if (newcap > data.length)
		{
			char [] temp = new char[newcap];
			for (int i = 0; i < len; i++)
				temp[i] = data[i];
			data = temp;
		}
	}
}
	