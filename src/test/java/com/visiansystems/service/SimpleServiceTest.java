//package com.visiansystems.service;
//
//import com.visiansystems.AbstractTest;
//import com.visiansystems.simpleobject.SimpleObject;
//import com.visiansystems.simpleobject.SimpleService;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityExistsException;
//import javax.persistence.NoResultException;
//import java.util.Collection;
//
//@Transactional
//public class SimpleServiceTest extends AbstractTest {
//
//    @Autowired
//    private SimpleService simpleService;
//
//    @Before
//    public void setUp() {
//    }
//
//    @Test
//    public void testGetSimpleObjects() {
//        Collection<SimpleObject> simpleObjects = simpleService.findAll();
//
//        Assert.assertNotNull("failure - expected not null", simpleObjects);
//        Assert.assertEquals("failure - expected  simpleObjects", 0, simpleObjects.size());
//    }
//
//    @Ignore
//    @Test
//    public void testGetGreeting() {
//
//        Long id = new Long(1);
//
//        SimpleObject greeting = simpleService.findOne(id);
//
//        Assert.assertNotNull("failure - expected not null", greeting);
//        Assert.assertEquals("failure - expected greeting.id match", id,
//                            greeting.getId());
//
//    }
//
//    @Ignore
//    @Test
//    public void testGetGreetingNotFound() {
//
//        Long id = Long.MAX_VALUE;
//
//        SimpleObject greeting = simpleService.findOne(id);
//
//        Assert.assertNull("failure - expected null", greeting);
//
//    }
//
//    @Ignore
//    @Test
//    public void testCreateGreeting() {
//
//        SimpleObject simpleObject = new SimpleObject();
//        simpleObject.setText("test");
//
//        SimpleObject createdGreeting = simpleService.create(simpleObject);
//
//        Assert.assertNotNull("failure - expected simpleObject not null",
//                             createdGreeting);
//        Assert.assertNotNull("failure - expected simpleObject.id not null",
//                             createdGreeting.getId());
//        Assert.assertEquals("failure - expected simpleObject.text match", "test",
//                            createdGreeting.getText());
//
//        Collection<SimpleObject> simpleObjects = simpleService.findAll();
//
//        Assert.assertEquals("failure - expected 3 simpleObjects", 3,
//                            simpleObjects.size());
//    }
//
//    @Ignore
//    @Test
//    public void testCreateGreetingWithId() {
//
//        Exception e = null;
//
//        SimpleObject greeting = new SimpleObject();
//        greeting.setId(Long.MAX_VALUE);
//        greeting.setText("test");
//
//        try {
//            SimpleObject createdGreeting = simpleService.create(greeting);
//        }
//        catch (EntityExistsException eee) {
//            e = eee;
//        }
//
//        Assert.assertNotNull("failure - expected exception", e);
//        Assert.assertTrue("failure - expected EntityExistsException",
//                          e instanceof EntityExistsException);
//
//    }
//
//    @Ignore
//    @Test
//    public void testUpdateGreeting() {
//
//        Long id = new Long(1);
//
//        SimpleObject greeting = simpleService.findOne(id);
//
//        Assert.assertNotNull("failure - expected greeting not null", greeting);
//
//        String updatedText = greeting.getText() + " test";
//        greeting.setText(updatedText);
//        SimpleObject updatedGreeting = simpleService.update(greeting);
//
//        Assert.assertNotNull("failure - expected updated greeting not null",
//                             updatedGreeting);
//        Assert.assertEquals("failure - expected updated greeting id unchanged",
//                            id, updatedGreeting.getId());
//        Assert.assertEquals("failure - expected updated greeting text match",
//                            updatedText, updatedGreeting.getText());
//
//    }
//
//    @Ignore
//    @Test
//    public void testUpdateGreetingNotFound() {
//
//        Exception e = null;
//
//        SimpleObject greeting = new SimpleObject();
//        greeting.setId(Long.MAX_VALUE);
//        greeting.setText("test");
//
//        try {
//            SimpleObject updatedGreeting = simpleService.update(greeting);
//        }
//        catch (NoResultException nre) {
//            e = nre;
//        }
//
//        Assert.assertNotNull("failure - expected exception", e);
//        Assert.assertTrue("failure - expected NoResultException",
//                          e instanceof NoResultException);
//
//    }
//
//    @Ignore
//    @Test
//    public void testDeleteGreeting() {
//
//        Long id = new Long(1);
//
//        SimpleObject greeting = simpleService.findOne(id);
//
//        Assert.assertNotNull("failure - expected greeting not null", greeting);
//
//        simpleService.delete(id);
//
//        Collection<SimpleObject> greetings = simpleService.findAll();
//
//        Assert.assertEquals("failure - expected 1 greeting", 1,
//                            greetings.size());
//
//        SimpleObject deletedGreeting = simpleService.findOne(id);
//
//        Assert.assertNull("failure - expected greeting to be deleted",
//                          deletedGreeting);
//
//    }
//}
