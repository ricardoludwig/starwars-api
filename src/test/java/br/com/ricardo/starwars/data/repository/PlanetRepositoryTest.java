package br.com.ricardo.starwars.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ricardo.starwars.model.Planet;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PlanetRepositoryTest {

	@Autowired
	private PlanetRepository pr;

	private Planet planet;

	@Before
	public void setUp() {

		planet = new Planet("Earth", "temperate", "forest");

	}

	@Test
	public void given_a_planet_should_save() {

		Planet saved = pr.save(planet);

		assertNotNull(saved);
		assertNotNull(saved.getId());
		assertEquals(planet.getName(), saved.getName());
		assertEquals(planet.getClimate(), saved.getClimate());
		assertEquals(planet.getTerrain(), saved.getTerrain());

	}
	
	@Test
	public void find_a_planet_by_id() {

		Planet saved = pr.save(planet);

		assertNotNull(saved);
		BigInteger id = saved.getId();
		assertNotNull(id);
		
		Optional<Planet> optional = pr.findById(id);
		
		assertTrue(optional.isPresent());
		
		Planet found = optional.get();
		assertEquals(planet.getId(), found.getId());

	}
	
	@Test
	public void find_a_planet_by_name() {

		Planet saved = pr.save(planet);

		assertNotNull(saved);
		String name  = saved.getName();
		assertNotNull(name);
		
		List<Planet> planets = pr.findByName(name);
		
		assertFalse(planets.isEmpty());
		
		Planet found = planets.get(0);
		assertEquals(planet.getName(), found.getName());

	}
	
	@Test
	public void given_a_planet_should_delete() {
		
		Planet saved = pr.save(planet);
		assertNotNull(saved.getId());
		
		pr.delete(saved);
		
		Optional<Planet> deleted = pr.findById(saved.getId());
		assertFalse(deleted.isPresent());
		
	}

}
