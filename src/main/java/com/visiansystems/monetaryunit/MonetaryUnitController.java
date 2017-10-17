package com.visiansystems.monetaryunit;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("primarate")
@Api(value = "/monetaryunit", description = "Operations on available publisher service.")
public class MonetaryUnitController {

    @Autowired
    private MonetaryUnitService monetaryUnitService;

    @RequestMapping(
            value = "/monetaryunit",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch all 'MonetaryUnit' entities", produces = "application/json")
    public ResponseEntity<Collection<MonetaryUnit>> getAll() {
        return new ResponseEntity<>(monetaryUnitService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/monetaryunit/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch a single 'MonetaryUnit' entity by primary key identifier", produces = "application/json")
    public ResponseEntity<MonetaryUnit> GetOneById(@PathVariable Long id) {
        MonetaryUnit unit = monetaryUnitService.findOne(id);

        if (unit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(unit, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/monetaryunit/code/{code}",  //TODO: What's the right std for multiple GETs?
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch a single 'MonetaryUnit' entity by currency code", produces = "application/json")
    public ResponseEntity<MonetaryUnit> GetOneByCode(@PathVariable String code) {
        MonetaryUnit unit = monetaryUnitService.findByCode(code);

        if (unit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(unit, HttpStatus.OK);
    }
}
