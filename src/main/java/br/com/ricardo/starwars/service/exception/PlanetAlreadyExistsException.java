package br.com.ricardo.starwars.service.exception;

public class PlanetAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -7916466337267639239L;

	public PlanetAlreadyExistsException() {
		super("planet.already.exists");
	}

}
