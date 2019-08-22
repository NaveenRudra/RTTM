package org.kafkaparser.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kafkaparser.utilities.DbUtil;
import org.kafkaparser.utilities.EmailUtility;
import org.sqlite.dataaccess.util.DaoUtil;

public class TruffleHog implements Runnable{
	
	 
	private static String regexForSecret = "stringsFound\": (.*)}";
	
	private String pastielink;
	private String searchTerm;
	private String profile;
	private String regex;
	private String entropy;
	private String botName;
	private String filePath;
	
	
	public void initilaize(String filePath,String pastielink,String searchTerm,String profile,String regex,String entropy)
	{
		this.filePath=filePath;
		this.pastielink = pastielink;
		this.searchTerm = searchTerm;
		this.profile = profile;
		this.regex=regex;
		this.entropy = entropy;
		
	}
	
	public ArrayList<String> getSecrets() throws IOException, InterruptedException
	{
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
			      "--entropy="+entropy,
			      "--json",
			      "file://"+filePath};
		
		
		
		
		
		
		Process p = Runtime.getRuntime().exec(cmd);
		   // p.waitFor();
		    BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		         String line;
		        while ((line = bri.readLine()) != null) {
		             //System.out.println(line);
		        	  //System.out.println();
		        	  secrets.addAll(extractRegexMatches(line,regexForSecret));
		        	  System.out.println(line);

		          }
		          bri.close();
		          while ((line = bre.readLine()) != null) {
		             //System.out.println(line);
		        	  secrets.addAll(extractRegexMatches(line,regexForSecret));
		        	  System.out.println(line);

		          }
		          bre.close();
		          p.waitFor();
		        
		    p.destroy();
		    Boolean is_Valid=false;
		    if(secrets.size()>0)
			{
		    	System.out.println("Issues have been found ************* Sending email");
		    	Set<String> temp=new HashSet<String>(); 
				temp.add(pastielink);
		    	EmailUtility.sendEmailUsingGmail(profile, temp, searchTerm);
		    	is_Valid=true;
			}
		    
		    if(!DaoUtil.searchDuplicateByUrl(pastielink))
			{
				DbUtil.addNewEntry(secrets, pastielink,profile,is_Valid);
			
		    }
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
