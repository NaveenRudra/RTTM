package org.kafkaparser.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.kafkaparser.pojo.Data;
import org.sqlite.dataaccess.entity.Result;
import org.sqlite.dataaccess.entity.SearchItem;
import org.sqlite.dataaccess.util.DaoUtil;
import org.sqlite.dataaccess.util.EMfactory;

public class DbUtil {

	private static DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	private static Date dateobj = new Date();
	private static String FUTURE_IMPLEMENTATION = "This is for future implementation";

	private static final SearchItem getSearchItem(final SearchItem searchItem) {
		try {
			final String query = "SELECT search_item FROM SearchItem search_item where search_item.searchItem=:searchItem";
			final TypedQuery<SearchItem> typedQuery = EMfactory.em.createQuery(query, SearchItem.class);
			typedQuery.setParameter("searchItem", searchItem.getSearchItem());
			return typedQuery.getResultList().stream().findFirst().orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addNewEntry(Set<SearchItem> termsFound, Data data) {

		final Result result = new Result();
		result.setSearchedTerms(termsFound);
		result.setSearchedtext(FUTURE_IMPLEMENTATION);
		result.setBotName(data.getBotName());
		result.setUrl(data.getUrl());
		result.setTime(df.format(dateobj).toString());
		SearchItem item = null;
		for (SearchItem searchItem : termsFound) {
			item = getSearchItem(searchItem);
			if(item != null) {
				searchItem.setId(item.getId());
			}
			searchItem.addResult(result);
		}
		if(item != null) {
			DaoUtil.merge(result);
		} else {
			DaoUtil.insert(result);	
		}
		
	}

	public static void addNewEntry(Set<SearchItem> termsFound, String url) {

		final Result result = new Result();
		result.setSearchedTerms(termsFound);
		result.setSearchedtext(FUTURE_IMPLEMENTATION);
		result.setBotName("Future");
		result.setUrl(url);
		result.setTime(df.format(dateobj).toString());
		for (SearchItem searchItem : termsFound) {
			final SearchItem item = getSearchItem(searchItem);
			searchItem.setId(item.getId());			
			searchItem.addResult(result);
		}
		DaoUtil.insert(result);
	}

	public static void addNewEntry(Set<SearchItem> termsFound, String url, String botName) {

		final Result result = new Result();
		result.setSearchedTerms(termsFound);
		result.setSearchedtext(FUTURE_IMPLEMENTATION);
		result.setBotName(botName);
		result.setUrl(url);
		result.setTime(df.format(dateobj).toString());
		for (SearchItem searchItem : termsFound) {
			final SearchItem item = getSearchItem(searchItem);
			searchItem.setId(item.getId());			
			searchItem.addResult(result);
		}
		DaoUtil.insert(result);
	}

	public static void addNewEntry(Set<SearchItem> termsFound, String url, String botName, Boolean isValid) {
		final Result result = new Result();
		result.setSearchedTerms(termsFound);
		result.setSearchedtext(FUTURE_IMPLEMENTATION);
		result.setBotName(botName);
		result.setUrl(url);
		result.setTime(df.format(dateobj).toString());
		result.setIsValid(isValid);
		for (SearchItem searchItem : termsFound) {
			final SearchItem item = getSearchItem(searchItem);
			searchItem.setId(item.getId());			
			searchItem.addResult(result);
		}
		DaoUtil.insert(result);
	}
}
