package org.sqlite.dataaccess.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

/**
 * 
 * @author Josue R G Junior josueribeiro.jr@gmail.com
 */
@Entity
public class Result implements Serializable {

	private static final long serialVersionUID = -7250234396452258822L;

	@Id
	@Column(name = "url",unique = true, updatable = false, nullable = false)
	private String url;
	private String time;
	private String searchedtext;
	@Column(length=1000000)
	@Lob
	@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="result_search_item", joinColumns = {
			@JoinColumn(name = "url", nullable = false)},
			inverseJoinColumns = { @JoinColumn(name = "id", 
			nullable = false)}
	)
	private Set<SearchItem> searchedItemSet = new HashSet<SearchItem>();
	@Column(name = "is_valid")
	private Boolean isValid;

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getBotName() {
		return botName;
	}

	public void setBotName(String botName) {
		this.botName = botName;
	}

	private String botName;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	// add one extra column from future perspective
	// add one extra column if it is false or true
	public Set<SearchItem> getSearchedTerms() {
		return searchedItemSet;
	}

	public void setSearchedTerms(Set<SearchItem> searchedItemSet) {
		this.searchedItemSet = searchedItemSet;
	}

	@Override
	public String toString() {
		return "Result [url=" + url + ", time=" + time + ", searchedtext=" + searchedtext + ", searchedItemSet="
				+ searchedItemSet + ", isValid=" + isValid + ", botName=" + botName + "]";
	}
	
}