package br.com.ricardo.starwars.data.remote;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.ricardo.starwars.data.remote.execption.RemoteServiceUnavailableException;
import br.com.ricardo.starwars.service.CollectionPlanet;

@Service
public class PlanetRemote {

	private static final Logger LOG = Logger.getLogger(PlanetRemote.class);
	
	private static final String HTTPS_SWAPI_CO_API_PLANETS = "https://swapi.co/api/planets";
	
	public CollectionPlanet getCollectionPlanet(int pageNumber) {

		ResponseEntity<CollectionPlanet> response = doGet(pageNumber);
		CollectionPlanet body = response.getBody();

		if (body == null)
			return new CollectionPlanet();

		return body;
	}
	
	public ResponseEntity<CollectionPlanet> doGet(int pageNumber) {

		if (pageNumber < 1)
			pageNumber = 1;

		HttpHeaders headers = buildHeaders();

		try {

			ResponseEntity<CollectionPlanet> response = new RestTemplate().exchange(
					HTTPS_SWAPI_CO_API_PLANETS + "?page=" + pageNumber, HttpMethod.GET,
					new HttpEntity<String>("parameters", headers), CollectionPlanet.class);
			return response;
			
		} catch (Exception ex) {
			LOG.error("Remote connection fail", ex);
			throw new RemoteServiceUnavailableException(ex);
		}

	}

	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		return headers;
	}

}
