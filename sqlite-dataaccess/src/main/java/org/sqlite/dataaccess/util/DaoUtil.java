package org.sqlite.dataaccess.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.sqlite.dataaccess.entity.Result;

public class DaoUtil {

	static 
	{
		EMfactory.setUp();
		EMfactory.initEntityManager();
	}
	
	@Transactional
	public synchronized static void insert(Result data)
	{	
			EMfactory.em.getTransaction().begin();
			EMfactory.em.persist(data);
			EMfactory.em.getTransaction().commit();

	}
	
	@Transactional
	public synchronized static boolean searchDuplicateByUrl(String url)
	{
		//System.out.println("In db url is : "+url);
		
		//TypedQuery<Result> query = EMfactory.em.createQuery(
		//		  "SELECT result FROM Result result where result.url='"+url+"'" , Result.class);
		TypedQuery<Result> query = EMfactory.em.createQuery(
				  "SELECT result FROM Result result where result.url=:url" , Result.class);
		query.setParameter("url", url);
		ArrayList<Result> results = (ArrayList<Result>) query.getResultList();
		
		//System.out.println("query size :"+Integer.toString(results.size()));
		if(results.size()>0)
		{
			return true;
		}
		return false;
	}
	
	public static void main (String [] args)
	{
//		Result person = new Result();
//		ArrayList<String> test=new ArrayList<>();
//		test.add("asd");
//		person.setSearchedTerms(test);
//		person.setSearchedtext("some lines up and down man");
//		person.setUrl("http://google.com4");
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		Date date = new Date();
//		person.setTime(dateFormat.format(date).toString());
		System.out.println("Stated intializing*****************************************************************************");
		for (int i=0;i<3;i++)
		{
			if(searchDuplicateByUrl("http://google.com71"))
			{
				System.out.println(Integer.toString(i) +"   *************************-----found");
				System.out.println(Integer.toString(i) +"   *************************-----found");
				System.out.println(Integer.toString(i) +"   *************************-----found");
			}
			
			else
			{
				System.out.println(Integer.toString(i) +"  *************************-----not found");
				System.out.println(Integer.toString(i) +"  *************************-----not found");
				System.out.println(Integer.toString(i) +"  *************************-----not found");
				
			}
			
			Result person = new Result();
			ArrayList<String> test=new ArrayList<>();
			test.add("asd");
			person.setSearchedTerms(test);
			person.setSearchedtext("some lines up and down man");
			person.setUrl("http://google.com71");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			person.setTime(dateFormat.format(date).toString());
			DaoUtil.insert(person);
			Result person1 = new Result();
			ArrayList<String> test1=new ArrayList<>();
			test1.add("asdq");
			person1.setSearchedTerms(test);
			person1.setSearchedtext("some lines up and down man");
			person1.setUrl("http://google.com712");
			DaoUtil.insert(person1);
			
		
		}
		//EMfactory.em.persist(person);
		//EMfactory.em.getTransaction().commit();
		
		
	}
}
