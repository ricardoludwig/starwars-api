package br.com.ricardo.starwars.service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ricardo.starwars.adapter.PageAdapter;
import br.com.ricardo.starwars.data.remote.PlanetRemote;
import br.com.ricardo.starwars.data.repository.PlanetRepository;
import br.com.ricardo.starwars.model.Planet;

@Service
public class PlanetService {

	@Autowired
	private PlanetRepository planetRepo;
	
	@Autowired
	private PlanetRemote remote;
	
	public Page<Planet> remoteFindAll(Pageable pageable) {
		
		int pageNumber = pageable.getPageNumber();
		CollectionPlanet collectionPlanet = remote.getCollectionPlanet(pageNumber);
		
		List<Planet> results = (List<Planet>) collectionPlanet.getResults();
		Page<Planet> page = new PageAdapter<>(results, pageable, collectionPlanet.getCount());
		
		return page;
				
	}

	public Planet findById(BigInteger id) {
		Optional<Planet> planetId = planetRepo.findById(id);
		if (planetId.isPresent()) {
			return planetId.get();
		}
		
		throw new IllegalStateException("Not found");
	}

	public Page<Planet> findAll(Pageable pageable) {
		Page<Planet> page = planetRepo.findAll(pageable);
		return page;
	}

	public List<Planet> findByName(String name) {
		List<Planet> planets = planetRepo.findByName(name);
		
		if (planets == null)
			Collections.emptyList();
		
		return planets;
	}

	public Planet update(Planet planet) {
		Planet updated = create(planet);
		return updated;
	}
	
	public Planet create(Planet planet) {
		Planet saved = planetRepo.save(planet);
		return saved;
	}

	public void deleteById(BigInteger id) {
		planetRepo.deleteById(id);
	}

}
