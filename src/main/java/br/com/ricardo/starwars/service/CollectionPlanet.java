package br.com.ricardo.starwars.service;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.ricardo.starwars.model.Planet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionPlanet {

	private Integer count;
	private String next;
	private String previous;

	private Collection<Planet> results;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public Collection<Planet> getResults() {
		return results;
	}

	public void setResults(Collection<Planet> results) {
		this.results = results;
	}

}
