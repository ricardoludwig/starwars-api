package br.com.ricardo.starwars.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ricardo.starwars.model.Planet;
import br.com.ricardo.starwars.service.PlanetService;

@RestController
@RequestMapping("/starwars/api/v1/planets")
public class PlanetController {

	@Autowired
	private PlanetService planetServ;
	
	@GetMapping("/swapi")
	public ResponseEntity<Response> getPlanets(Pageable pageable) {
		
		Page<Planet> planets = planetServ.remoteFindAll(pageable);

		Response response = new Response(planets, "planets.found");
		
		if (pageable.hasPrevious()) {
			Pageable previous = pageable.previousOrFirst();
			int pageNumber = previous.getPageNumber();
			Link lnkPrevious = linkTo(methodOn(PlanetController.class).getPlanets(pageable.previousOrFirst()))
					.slash("?page=" + pageNumber).withRel("previous");
			response.add(lnkPrevious);
		}
		
		response.add(linkTo(methodOn(PlanetController.class). getPlanets(pageable)).withSelfRel());
		
		Pageable next = pageable.next();
		if (next != null) {
			int pageNumber = next.getPageNumber();
			Link lnkNext = linkTo(methodOn(PlanetController.class).getPlanets(pageable.next()))
					.slash("?page=" + pageNumber).withRel("next");
			response.add(lnkNext);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/ids/{idNumber}")
	public ResponseEntity<Response> getById(@PathVariable(value = "idNumber", required = true) BigInteger id) {

		Planet planet = planetServ.findById(id);

		Response response = new Response(planet, "planets.found");
		response.add(linkTo(methodOn(PlanetController.class).getById(id)).withSelfRel());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/names/{name}")
	public ResponseEntity<Response> getByName(@PathVariable(value = "name", required = true) String name) {

		List<Planet> planets = planetServ.findByName(name);

		Response response = new Response(planets, "planet.found");
		response.add(linkTo(methodOn(PlanetController.class).getByName(name)).withSelfRel());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Response> create(@RequestBody Planet req) {
		
		BigInteger id = req.getId();
		if (id != null) {
			Planet found = planetServ.findById(id);
			if (found != null) {
				Response response = new Response(found, "planet.already.exists");
				response.add(linkTo(methodOn(PlanetController.class).create(req)).withSelfRel());
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		
		Planet planet = planetServ.create(req);
		
		Response response = new Response(planet, "planet.created");
		response.add(linkTo(methodOn(PlanetController.class).create(req)).withSelfRel());
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
	}
	
	@PutMapping
	public ResponseEntity<Response> update(@RequestBody Planet req) {
		
		Planet planet = planetServ.update(req);
		
		Response response = new Response(planet, "planet.updated");
		response.add(linkTo(methodOn(PlanetController.class).update(req)).withSelfRel());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/ids/{idNumber}")
	public ResponseEntity<Response> deleteById(@PathVariable(value = "idNumber", required = true) BigInteger id) {
		
		planetServ.deleteById(id);

		Response response = new Response(id, "planet.deleted");
		response.add(linkTo(methodOn(PlanetController.class).deleteById(id)).withSelfRel());

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping()
	public ResponseEntity<Response> getAll(Pageable pageable) {

		Page<Planet> planets = planetServ.findAll(pageable);

		Response response = new Response(planets, "planets.found");

		if (pageable.hasPrevious()) {
			Pageable previous = pageable.previousOrFirst();
			int pageNumber = previous.getPageNumber();
			Link lnkPrevious = linkTo(methodOn(PlanetController.class).getAll(pageable.previousOrFirst()))
					.slash("?page=" + pageNumber).withRel("previous");
			response.add(lnkPrevious);
		}

		Link self = linkTo(methodOn(PlanetController.class).getAll(pageable)).withSelfRel();
		response.add(self);

		Pageable next = pageable.next();
		if (next != null) {
			int pageNumber = next.getPageNumber();
			Link lnkNext = linkTo(methodOn(PlanetController.class).getAll(pageable.next()))
					.slash("?page=" + pageNumber).withRel("next");
			response.add(lnkNext);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}	

}
