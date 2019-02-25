package br.com.ricardo.starwars.data.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.ricardo.starwars.model.Planet;

public interface PlanetRepository extends PagingAndSortingRepository<Planet, BigInteger> {

	@Override
	Optional<Planet> findById(BigInteger id);

	Optional<Planet> findByName(String name);

}
