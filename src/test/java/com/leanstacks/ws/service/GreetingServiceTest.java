package com.leanstacks.ws.service;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.leanstacks.ws.AbstractTest;
import com.leanstacks.ws.model.Greeting;

@Transactional
public class GreetingServiceTest extends AbstractTest {

    @Autowired
    private GreetingService greetingService;

    @Before
    public void setUp() {
        greetingService.evictCache();
    }

    @Test
    public void testGetGreetings() {

        Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertNotNull("failure - expected not null", greetings);
        Assert.assertEquals("failure - expected 2 greetings", 2,
                greetings.size());

    }

    @Test
    public void testGetGreeting() {

        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("Number of greetings is: {}", greetings.size());

        Long id = new Long(1);

        Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected not null", greeting);
        Assert.assertEquals("failure - expected greeting.id match", id,
                greeting.getId());

    }

    @Test
    public void testCreateGreeting() {

        Greeting greeting = new Greeting();
        greeting.setText("test");

        Greeting createdGreeting = greetingService.create(greeting);

        Assert.assertNotNull("failure - expected greeting not null",
                createdGreeting);
        Assert.assertNotNull("failure - expected greeting.id not null",
                createdGreeting.getId());
        Assert.assertEquals("failure - expected greeting.text match", "test",
                createdGreeting.getText());

        Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertEquals("failure - expected 3 greetings", 3,
                greetings.size());

    }

    @Test
    public void testUpdateGreeting() {

        Long id = new Long(1);

        Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected greeting not null", greeting);

        String updatedText = greeting.getText() + " test";
        greeting.setText(updatedText);
        Greeting updatedGreeting = greetingService.update(greeting);

        Assert.assertNotNull("failure - expected updated greeting not null",
                updatedGreeting);
        Assert.assertEquals("failure - expected updated greeting id unchanged",
                id, updatedGreeting.getId());
        Assert.assertEquals("failure - expected updated greeting text match",
                updatedText, updatedGreeting.getText());

    }

    @Test
    public void testDeleteGreeting() {

        Long id = new Long(1);

        Greeting greeting = greetingService.findOne(id);

        Assert.assertNotNull("failure - expected greeting not null", greeting);

        greetingService.delete(id);

        Collection<Greeting> greetings = greetingService.findAll();

        Assert.assertEquals("failure - expected 1 greeting", 1,
                greetings.size());

        Greeting deletedGreeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected greeting to be deleted",
                deletedGreeting);

    }

}
