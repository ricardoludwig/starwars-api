package br.com.ricardo.starwars.data.remote.execption;

public class RemoteServiceUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String REMOTE_SERVICE_UNAVAILABLE = "remote.service.unavailable";

	public RemoteServiceUnavailableException() {
		super(REMOTE_SERVICE_UNAVAILABLE);
	}

	public RemoteServiceUnavailableException(Throwable ex) {
		super(REMOTE_SERVICE_UNAVAILABLE, ex);
	}

}
