package com.visiansystems.simpleobject;

import com.visiansystems.simpleobject.SimpleObject;
import com.visiansystems.simpleobject.SimpleService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * The SimpleController class is a RESTful web service controller. This controller is simple, and
 * create static objects just to illustrate how the endpoints should work without any database
 * integration. It performs the basic CRUD operations.
 */
@RestController
// --> 'RestController' annotation informs Spring that should convert the objects returned from
// controller methods into json or XML responses.
@RequestMapping("tutorial")
@Api(value = "Endpoint: /tutorial", description = "Operations on available publisher service.")
public class SimpleController {

    @Autowired
    private SimpleService simpleService;

    /**
     * Web service endpoint to fetch all 'SimpleObject' entities.
     *
     * @return The service returns the collection of entities as JSON.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/simple",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch all 'SimpleObject' entities", produces = "application/json")
    public ResponseEntity<Collection<SimpleObject>> getSimpleObjects() {
        return new ResponseEntity<>(simpleService.findAll(), HttpStatus.OK);
    }

    /**
     * Web service endpoint to fetch a single 'SimpleObject' entity by primary key identifier.
     * <p/>
     * If found, the Greeting is returned as JSON with HTTP status 200.
     * If not found, the service returns an empty response body with HTTP status 404.
     *
     * @param id A Long URL path variable containing the 'SimpleObject' identifier.
     * @return A ResponseEntity containing a single 'SimpleObject' entity, if found, and a HTTP status
     * code as described in the method comment.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/simple/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch a single 'SimpleObject' entity by primary key identifier", produces = "application/json")
    public ResponseEntity<SimpleObject> getSimpleObject(@PathVariable Long id) {
        SimpleObject simpleObject = simpleService.findOne(id);

        if (simpleObject == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(simpleObject, HttpStatus.OK);
    }

    /**
     * Web service endpoint to create a single 'SimpleObject' entity. The HTTP request body is
     * expected to contain a entity in JSON format.
     * <p/>
     * If created successfully, the persisted 'SimpleObject' is returned as JSON with HTTP status 201.
     * If not created successfully, the service returns an empty response body with HTTP status 500.
     *
     * @param simpleObject The object to be created.
     * @return A ResponseEntity containing a single object, if created successfully, and a HTTP
     * status code as described in the method comment.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/simple",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Persist a single 'SimpleObject' entity by primary key identifier",
            produces = "application/json", consumes = "application/json")
    public ResponseEntity<SimpleObject> createSimpleObject(@RequestBody SimpleObject simpleObject) {
        return new ResponseEntity<>(simpleService.create(simpleObject), HttpStatus.CREATED);
    }

    /**
     * Web service endpoint to update a single 'SimpleObject' entity. The HTTP request body is expected to
     * contain a 'SimpleObject' in JSON format. The object is updated in the data repository.
     * <p/>
     * If updated successfully, the persisted Greeting is returned as JSON with HTTP status 200.
     * If not found, the service returns an empty response body and HTTP status 404.
     * If not updated successfully, the service returns an empty response body with HTTP status 500.
     *
     * @param simpleObject The 'SimpleObject' to be updated.
     * @return A ResponseEntity containing a single 'SimpleObject', if updated successfully, and a
     * HTTP status code as described in the method comment.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/simple/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update a single 'SimpleObject' entity by primary key identifier",
            produces = "application/json", consumes = "application/json")
    public ResponseEntity<SimpleObject> updateSimpleObject(@RequestBody SimpleObject simpleObject) {
        return new ResponseEntity<>(simpleService.update(simpleObject), HttpStatus.OK);
    }

    /**
     * Web service endpoint to delete a single 'SimpleObject' entity. The HTTP request body is empty.
     * The primary key identifier of the Greeting to be deleted is supplied in the URL as a path variable.
     * <p/>
     * If deleted successfully, the service returns an empty response body with HTTP status 204.
     * If not deleted successfully, the service returns an empty response body with HTTP status 500.
     *
     * @param id A Long URL path variable containing the Greeting primary key identifier.
     * @return A ResponseEntity with an empty response body and a HTTP status code as described in the method comment.
     * @throws Exception Throw if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/simple/{id}",
            method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a single 'SimpleObject' entity by primary key identifier")
    public ResponseEntity<SimpleObject> deleteSimpleObject(@PathVariable("id") Long id)
            throws Exception {
        simpleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
