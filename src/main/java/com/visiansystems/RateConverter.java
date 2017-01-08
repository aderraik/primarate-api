package com.visiansystems;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.visiansystems.exception.BankRateFeedException;
import com.visiansystems.rates.BankRates;

@Component
@Path("/convert")
@Produces(MediaType.APPLICATION_JSON)
public class RateConverter {
	

	@Autowired
    private BankRates bank;

	@GET
	@Path("/{centralBankId}/{currencyIn}/{amount}/{currencyOut}")
	public Double convert(@PathParam("centralBankId") Long centralBankId, @PathParam("currencyIn") String currencyIn, @PathParam("amount") Double amount, @PathParam("currencyOut") String currencyOut) throws IllegalArgumentException, BankRateFeedException {
		return bank.convert(centralBankId, amount, currencyIn, currencyOut);
	}
	
}
