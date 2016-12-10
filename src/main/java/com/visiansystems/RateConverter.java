package com.visiansystems;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/convert")
@Produces(MediaType.APPLICATION_JSON)
public class RateConverter {

	@GET
	@Path("/{currencyIn}/{amount}/{currencyOut}")
	public Long convert(@PathParam("currencyIn") String currencyIn, @PathParam("amount") Long amount, @PathParam("currencyOut") String currencyOut) {
		return amount; 
	}
	
}
