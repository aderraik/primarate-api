package com.visiansystems;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.visiansystems.bl.bankRateFeed.BankRateFeed;
import com.visiansystems.exception.BankRateFeedException;

@Component
@Path("/convert")
@Produces(MediaType.APPLICATION_JSON)
public class RateConverter {
	

	@Autowired
    @Qualifier("bcbRateFeed")	
    private BankRateFeed bcbBankRateFeed;

	@GET
	@Path("/{currencyIn}/{amount}/{currencyOut}")
	public Double convert(@PathParam("currencyIn") String currencyIn, @PathParam("amount") Double amount, @PathParam("currencyOut") String currencyOut) throws IllegalArgumentException, BankRateFeedException {
		//return bcbBankRateFeed.convert(amount, currencyIn, currencyOut);
		return amount;
	}
	
}
