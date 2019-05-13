package com.excilys.computer_database.console;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.excilys.computer_database.binding_dto.ComputerDto;

public class RestClient {
	  
    private static final String URL 
      = "http://localhost:8080/webapp/computers/";
  
    private Client client = ClientBuilder.newClient();
 
    public ComputerDto showDetails(int id) {
        return client
          .target(URL)
          .path(String.valueOf(id))
          .request(MediaType.APPLICATION_JSON)
          .get(ComputerDto.class);
    }

	public List<ComputerDto> findAll() {
		return client
		  .target(URL + "?size=1000")
		  .request(MediaType.APPLICATION_JSON)
		  .get(new GenericType<List<ComputerDto>> () {});
	}
	
	public int create(ComputerDto computer) {
		return client.target(URL)
			  .request(MediaType.APPLICATION_JSON)
			  .post(Entity.json(computer))
			  .getStatus();
	}
	
	public int update(ComputerDto computer) {
		return client.target(URL)
			  .request(MediaType.APPLICATION_JSON)
			  .put(Entity.json(computer))
			  .getStatus();
	}
	
	public int delete(int id) {
		return client.target(URL)
			  .path(String.valueOf(id))
			  .request(MediaType.APPLICATION_JSON)
			  .delete()
			  .getStatus();
	}
}