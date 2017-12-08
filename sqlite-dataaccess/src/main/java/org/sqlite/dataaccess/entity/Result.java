package org.sqlite.dataaccess.entity;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Josue R G Junior josueribeiro.jr@gmail.com
 */
@Entity
public class Result implements Serializable {

	private static final long serialVersionUID = -7250234396452258822L;

	@Id
	@Column(name = "id_scrapper")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; 
	private String url;
	private String time;
	private String searchedtext;
	private ArrayList<String> searchedTerms;
	
	//add one extra column from future perspective
	//add one extra column if it is false or true
	public ArrayList<String> getSearchedTerms() {
		return searchedTerms;
	}

	public void setSearchedTerms(ArrayList<String> searchedTerms) {
		this.searchedTerms = searchedTerms;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSearchedtext() {
		return searchedtext;
	}

	public void setSearchedtext(String searchedtext) {
		this.searchedtext = searchedtext;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



}