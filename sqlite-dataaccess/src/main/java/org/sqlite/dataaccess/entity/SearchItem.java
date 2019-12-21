package org.sqlite.dataaccess.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * @author Sunny Sharma sunnysharmagts@gmail.com
 */
@Entity
@Table(name = "search_item")
public class SearchItem implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, updatable=false, nullable = false)
	private Integer id;

	@Column(name = "search_term", unique = true, nullable = false)
	private String searchItem;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "searchedItemSet")
	private Set<Result> resultSet = new HashSet<Result>();

	public Integer getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getSearchItem() {
		return searchItem;
	}

	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}

	public Set<Result> getResult() {
		return this.resultSet;
	}

	public void setResult(Set<Result> resultSet) {
		this.resultSet = resultSet;
	}
	
	public void addResult(Result result) {
		this.resultSet.add(result);
		result.getSearchedTerms().add(this);
	}
	
	public void removeResult(Result result) {
		this.resultSet.remove(result);
		result.getSearchedTerms().remove(this);
	}

	@Override
	public String toString() {
		return "SearchItem [id=" + id + ", searchItem=" + searchItem + "]";
	}
}