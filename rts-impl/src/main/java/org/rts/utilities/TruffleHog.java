package org.rts.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kafkaparser.utilities.ConfigData;
import org.kafkaparser.utilities.DbUtil;
import org.kafkaparser.utilities.EmailUtility;
import org.sqlite.dataaccess.util.DaoUtil;

public class TruffleHog implements Runnable{
	
	 
	private static String regexForSecret = "stringsFound\": (.*)}";
	
	private String link;
	private String searchTerm;
	private String profile;
	private String regex;
	private String entropy;
	private String pythonPath;
	private String trufflehogPath;
	
	
	public void initilaize(String pastielink,String searchTerm,String profile,String regex,String entropy)
	{
		this.link = pastielink;
		this.searchTerm = searchTerm;
		this.profile = profile;
		this.regex=regex;
		this.entropy = entropy;
		
	}
	
	public ArrayList<String> getSecrets() throws IOException, InterruptedException
	{
		
	    System.out.println("*********Entered trufflehog");

		ArrayList<String> secrets = new ArrayList<>();
		
		if(regex.equals("false"))
		{
			regex="";
		}
		else
		{
			regex="--regex";
		}
		
		
		String[] cmd = {
			      //"/usr/local/bin/python2.7",
				  //"/usr/bin/python2.7",
			      //"/Users/n0r00ij/Downloads/truffleHog-dev/truffleHog/truffleHog/truffleHog.py",
				  ConfigData.pythonPath,
				  ConfigData.trufflehogPath,
			      regex,
			      "--cleanup",
			      "--entropy="+entropy,
			      "--json",
			      link};
		
		
		
		
		
			if(!DaoUtil.searchDuplicateByUrl(link))
			{
			Process p = Runtime.getRuntime().exec(cmd);
			
		   // p.waitFor();
		    BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		          String line;
		        while ((line = bri.readLine()) != null) {
		            //System.out.println(line);
		        	  //System.out.println();
		        	  secrets.addAll(extractRegexMatches(line,regexForSecret));

		          }
		          bri.close();
		          while ((line = bre.readLine()) != null) {
		            //System.out.println(line);
		        	  secrets.addAll(extractRegexMatches(line,regexForSecret));

		          }
		          bre.close();
		          //Important decide if this is needed or remove it
		          p.waitFor(5,TimeUnit.MINUTES);
		          Field f;
		          int pid=1111111;
				try {
					f = p.getClass().getDeclaredField("pid");
					f.setAccessible(true);
			        pid = (Integer)f.get(p);
			        System.out.println("******************"+pid);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		          
		          p.destroyForcibly();
		          
		   // p.destroy();
		    
			}
		    
		    
		    Boolean is_Valid=false;
		    if(secrets.size()>0)
			{
		    	Set<String> temp=new HashSet<String>(); 
				temp.add(link);
		    	EmailUtility.sendEmailUsingGmail(profile, temp, searchTerm);
		    	is_Valid=true;
			}
		    
		    if(!DaoUtil.searchDuplicateByUrl(link))
			{

				DbUtil.addNewEntry(secrets, link,profile,is_Valid);
			
		    }
		   // System.out.println("*********Done");
		    
		    
		return secrets;
	}
	
	public static ArrayList<String> extractRegexMatches(String line,String regex)
	{
		ArrayList<String> matches= new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex,Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			matches.add(matcher.group(1));
		    }
		
		return matches;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			getSecrets();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**public static void main(String args[])
	{
		try {
			System.out.println(getSecrets("https://github.com/cogdog/tweets.git"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}**/

}
