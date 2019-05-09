package com.excilys.computer_database.console;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.excilys.computer_database.binding_dto.ComputerDto;

public class RestClient {
	  
    private static final String FIND_BY_ID 
      = "http://localhost:8080/webapp/computers/";
  
    private Client client = ClientBuilder.newClient();
 
    public ComputerDto showDetails(int id) {
        return client
          .target(FIND_BY_ID)
          .path(String.valueOf(id))
          .request(MediaType.APPLICATION_JSON)
          .get(ComputerDto.class);
    }
    
}