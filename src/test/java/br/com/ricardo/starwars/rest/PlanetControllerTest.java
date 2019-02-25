package br.com.ricardo.starwars.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import br.com.ricardo.starwars.model.Planet;
import br.com.ricardo.starwars.service.PlanetService;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {
	
	//TODO adicionar validacao
	//TODO adicinar tramento de exceptions
	
	//TODO criar os testes unitarios (casos de erro, validar paginacao)
	
	//TODO deploy no heroku
	//TODO escrever o README
	//TODO Add swagger
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlanetService service;
	
	private Planet planet;
	
	private String planetJson;
	
	private List<Planet> planets;
	
	@Before
	public void setUp() {
		
		planet = new Planet("Earth", "temperate", "forest");
		
		planets = new ArrayList<>(1);
		planets.add(planet);
		
		planetJson = toJSon(planet);
		
	}
	
	@Test
	public void create_a_planet() throws Exception {
		
		when(service.create(any())).thenReturn(planet);

		mockMvc.perform(
				(post("/starwars/api/v1/planets").contentType(MediaType.APPLICATION_JSON).content(planetJson)))
					.andDo(print())
					.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void get_planet_by_id() throws Exception {
		
		when(service.findById(any())).thenReturn(planet);

		String queryFindByLogin = "/starwars/api/v1/planets/ids/2";
		mockMvc.perform(
				get(queryFindByLogin))
					.andDo(print())
					.andExpect(status().isOk());
	}
	
	@Test
	public void get_planet_by_name() throws Exception {
		
		when(service.findByName(anyString())).thenReturn(planets);

		String queryFindByLogin = "/starwars/api/v1/planets/names/Earth";
		mockMvc.perform(
				get(queryFindByLogin))
					.andDo(print())
					.andExpect(status().isOk());
	}
	
	@Test
	public void update_a_planet() throws Exception {
		
		when(service.update(any())).thenReturn(planet);

		mockMvc.perform(
				(put("/starwars/api/v1/planets").contentType(MediaType.APPLICATION_JSON).content(planetJson)))
					.andDo(print())
					.andExpect(status().isOk());
	}
	
	@Test
	public void delete_planet_by_id() throws Exception {
		
		doNothing().when(service).deleteById(any());

		mockMvc.perform(
				delete("/starwars/api/v1/planets/ids/1"))
					.andDo(print())
					.andExpect(status().isOk());

	}
	
	private String toJSon(Planet planet) {
		Gson gson = new Gson();
		String json = gson.toJson(planet);
		return json;
	}

}
