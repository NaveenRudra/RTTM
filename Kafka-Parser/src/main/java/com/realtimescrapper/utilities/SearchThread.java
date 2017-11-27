package com.realtimescrapper.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.realtimescrapper.dao.entity.Result;
import com.realtimescrapper.dao.util.DaoUtil;
import com.relatimescrapper.pojo.Data;

import net.amygdalum.stringsearchalgorithms.io.StringCharProvider;
import net.amygdalum.stringsearchalgorithms.search.Horspool;
import net.amygdalum.stringsearchalgorithms.search.StringFinder;

public class SearchThread implements Runnable{

	private Data data;
	private static DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	private static Date dateobj = new Date();
	private Horspool stringSearch;
	
	public void initialize(Data data)
	{
		this.data=data;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		String response=null;
		try {
			response = HttpUtilities.sendGet(data.getUrl());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> termsFound = new ArrayList<String>();
		for(String s:data.getSearchTerms())
		{
			/**
			if(response.contains(s))
			{
				termsFound.add(s);
			}**/
			stringSearch = new Horspool(s);
			StringFinder finder = stringSearch.createFinder(new StringCharProvider(response, 0));
			if(finder.findAll().size()>0)
			{
				termsFound.add(s);
			}
		}
		if(termsFound.size()>0)
		{
			//check if multiple threads are resulting in reading same data again and again over ok
  		    System.out.println(df.format(dateobj)+"found in **************************************************"+data.getUrl()+" data found is "+termsFound.get(0));				

  		   if(!DaoUtil.searchDuplicateByUrl(data.getUrl()))
			{
				//EmailUtility.sendEmailUsingGmail("Later", data.getUrl(), termsFound);
				Result result = new Result();
				result.setSearchedTerms(termsFound);
	     		result.setSearchedtext("This is for future implementation");
				result.setUrl(data.getUrl());
				result.setTime(df.format(dateobj).toString());
				DaoUtil.insert(result);
			}
  		   
		}
		
		
	}

}
