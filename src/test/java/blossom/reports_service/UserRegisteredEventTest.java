package blossom.reports_service;

import org.junit.jupiter.api.Test;

import blossom.reports_service.model.UserRegisteredEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserRegisteredEventTest {

    @Test
    public void testConstructor() {
        Integer id = 1;
        String name = "John Doe";
        String email = "john.doe@example.com";

        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(id, name, email);

        assertNotNull(userRegisteredEvent);
        assertEquals(id, userRegisteredEvent.id());
        assertEquals(name, userRegisteredEvent.name());
        assertEquals(email, userRegisteredEvent.email());
    }
}