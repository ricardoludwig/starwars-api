package br.com.ricardo.starwars.model;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "PLANET")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigInteger id;

	private String name;
	private String climate;
	private String terrain;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> films;
	
	@Transient
	private Integer amountFilms;
	
	public Planet(String name, String climate, String terrain, Integer amountFilmes) {
		this(name, climate, terrain);
		this.amountFilms = amountFilmes;
	}
	
	public Planet(String name, String climate, String terrain) {
		super();
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}
	
	public Planet() {
		super();
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}

	public Integer getAmountFilms() {
		if (amountFilms == null) {
			if (CollectionUtils.isEmpty(films))
				return 0;
			amountFilms = films.size();
		}
		return amountFilms;
	}

	public void setAmountFilms(Integer amountFilms) {
		this.amountFilms = amountFilms;
	}
	
	

}
