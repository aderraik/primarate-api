package com.visiansystems.rate;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("primarate")
@Api(value = "/rate", description = "Operations on available publisher service.")
public class RateController {

    @Autowired
    private RateService service;

    @RequestMapping(
            value = "/rate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch all 'MonetaryUnit' entities", produces = "application/json")
    public ResponseEntity<Collection<Rate>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
