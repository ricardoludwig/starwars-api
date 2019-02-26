package br.com.ricardo.starwars.service.exception;

public class PlanetNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlanetNotFoundException() {
		super("planet.not.found");
	}

}
