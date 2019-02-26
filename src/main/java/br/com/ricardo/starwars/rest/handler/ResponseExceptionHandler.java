package br.com.ricardo.starwars.rest.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.ricardo.starwars.data.remote.execption.RemoteServiceUnavailableException;
import br.com.ricardo.starwars.rest.PlanetController;
import br.com.ricardo.starwars.service.exception.PlanetAlreadyExistsException;
import br.com.ricardo.starwars.service.exception.PlanetNotFoundException;

@ControllerAdvice(basePackageClasses = PlanetController.class)
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseBody
	@ExceptionHandler(PlanetNotFoundException.class)
	ResponseEntity<?> handlePlanetNotFound(WebRequest request, PlanetNotFoundException ex) {
		ResponseHandler respEx = new ResponseHandler(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(respEx, HttpStatus.NOT_FOUND);
	}

	@ResponseBody
	@ExceptionHandler(PlanetAlreadyExistsException.class)
	ResponseEntity<?> handlePlanetAlreadyExists(WebRequest request, PlanetAlreadyExistsException ex) {
		ResponseHandler respEx = new ResponseHandler(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(respEx, HttpStatus.CONFLICT);
	}
	
	@ResponseBody
	@ExceptionHandler(RemoteServiceUnavailableException.class)
	ResponseEntity<?> handleInvalidNameException(WebRequest request, RemoteServiceUnavailableException ex) {
		ResponseHandler respEx = new ResponseHandler(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(respEx, HttpStatus.SERVICE_UNAVAILABLE);
	}

	class ResponseHandler {

		private final Date timestamp;
		private final String message;
		private final String details;

		ResponseHandler(Date timestamp, String message, String details) {
			super();
			this.timestamp = timestamp;
			this.message = message;
			this.details = details;
		}

		public Date getTimestamp() {
			return timestamp;
		}

		public String getMessage() {
			return message;
		}

		public String getDetails() {
			return details;
		}

	}

}
