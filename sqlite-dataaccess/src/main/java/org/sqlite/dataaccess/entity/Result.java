package org.sqlite.dataaccess.entity;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 
 * @author Josue R G Junior josueribeiro.jr@gmail.com
 */
@Entity
public class Result implements Serializable {

	private static final long serialVersionUID = -7250234396452258822L;

	@Id
	@Column(name = "url",unique = true, nullable = false)
	//@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	//private Integer id;
	private String url;
	private String time;
	private String searchedtext;
	@Column(length=1000000)
	@Lob
	private ArrayList<String> searchedTerms;
	private Boolean isValid;
	@Column(length=1000000)
	@Lob
	private ArrayList<String> secrets;
	private Boolean is_valid;
	
	
	public Boolean getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(Boolean is_valid) {
		this.is_valid = is_valid;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public ArrayList<String> getSecrets() {
		return secrets;
	}

	public void setSecrets(ArrayList<String> secrets) {
		this.secrets = secrets;
	}



	public String getBotName() {
		return botName;
	}

	public void setBotName(String botName) {
		this.botName = botName;
	}

	private String botName;

	// add one extra column from future perspective
	// add one extra column if it is false or true
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

	/**public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}**/

}