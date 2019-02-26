package br.com.ricardo.starwars.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.ricardo.starwars.adapter.PageAdapter;
import br.com.ricardo.starwars.data.remote.PlanetRemote;
import br.com.ricardo.starwars.data.repository.PlanetRepository;
import br.com.ricardo.starwars.model.Planet;
import br.com.ricardo.starwars.service.exception.PlanetAlreadyExistsException;
import br.com.ricardo.starwars.service.exception.PlanetNotFoundException;

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

		if (CollectionUtils.isEmpty(results))
			throw new PlanetNotFoundException();
		
		List<Planet> noFilmsLink = new ArrayList<>();
		for (Planet planet : results) {
			Planet planetNoLinkFilms = new Planet(planet.getName(), planet.getClimate(), planet.getTerrain(),
					planet.getAmountFilms());
			noFilmsLink.add(planetNoLinkFilms);
		}

		Page<Planet> page = new PageAdapter<>(noFilmsLink, pageable, collectionPlanet.getCount());

		return page;

	}

	public Planet findById(BigInteger id) {

		Optional<Planet> planetId = planetRepo.findById(id);

		if (planetId.isPresent()) {
			return planetId.get();
		}

		throw new PlanetNotFoundException();
	}

	public Page<Planet> findAll(Pageable pageable) {

		Page<Planet> page = planetRepo.findAll(pageable);

		if (CollectionUtils.isEmpty(page.getContent()))
			throw new PlanetNotFoundException();

		return page;
	}

	public List<Planet> findByName(String name) {

		List<Planet> planets = planetRepo.findByName(name);

		if (CollectionUtils.isEmpty(planets))
			throw new PlanetNotFoundException();

		return planets;
	}

	public Planet update(Planet planet) {
		
		BigInteger id = planet.getId();
		if (id == null) {
			throw new PlanetNotFoundException();
		}
		
		Planet updated = planetRepo.save(planet);
		
		return updated;
	}

	public Planet create(Planet planet) {

		BigInteger id = planet.getId();
		if (id != null) {
			Planet found = findById(id);
			if (found != null) {
				throw new PlanetAlreadyExistsException();
			}
		}

		Planet saved = planetRepo.save(planet);
		return saved;
	}

	public void deleteById(BigInteger id) {
		
		try {
			planetRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new PlanetNotFoundException();
		}
		
	}

}
