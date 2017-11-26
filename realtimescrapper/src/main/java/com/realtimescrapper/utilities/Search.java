package com.realtimescrapper.utilities;

import java.util.ArrayList;

public class Search 
{
	
	public static ArrayList<String> find(String response,String [] searchTerms)
	{
		ArrayList<String> termsFound = new ArrayList<String>();
		for(String s:searchTerms)
		{
			if(response.contains(s))
			{
				termsFound.add(s);
			}
		}
		return termsFound;
	}

}
