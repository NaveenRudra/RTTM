package com.realtimescrapper.dao.util;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EMfactory {
	
	public static EntityManagerFactory emf;
	public static EntityManager em;
	
	public static void setUp() {
		emf = Persistence.createEntityManagerFactory("Sqlite-DataAccess");
	}
	
	public static void initEntityManager() {
		em = emf.createEntityManager();
		
		
	}

}
