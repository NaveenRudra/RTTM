package org.rts.utilities;

import java.util.Set;

public class Difference 
{
	
	public static Set<String> getDiff(Set<String> previousSet,Set<String> presentSet)
	{
		presentSet.removeAll(previousSet);
		return presentSet;
	}

	public static void main(String args [])
	{
//		Set test1 = new HashSet();
//		test1.add("test1");
//		test1.add("test2");
//		test1.add("test3");
//		test1.add("test5");
//		test1.add("test6");
//
//		Set test2 = new HashSet();
//		test2.add("test1");
//		test2.add("test2"); 
//		test2.add("test3");
//		test2.add("test4");
//		test2.add("test5");
//		
//		Iterator i = getDiff(test1,test2).iterator();
//		while (i.hasNext())
//		  {
//			String name = (String) i.next();
//		    System.out.println(name);
//		  }

	}
	
	

}
