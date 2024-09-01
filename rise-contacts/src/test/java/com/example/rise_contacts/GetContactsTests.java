package com.example.rise_contacts;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class GetContactsTests {

    private MockedStatic<DBManager> dbManagerMock;
    private ContactsController contactsController;
    
    @BeforeEach
    public void setUp() {
        dbManagerMock = mockStatic(DBManager.class);
        contactsController = new ContactsController();
    }

    @AfterEach
    public void tearDown() {
        dbManagerMock.close();
    }

    @Test
    public void testGetContactsNotEmpty() {
        List<Contact> mockContacts = new ArrayList<>();
        Contact contact1 = new Contact("John", "Doe", "+1-555-123-4567", "123 Elm Street, Springfield, IL, 62701");
        Contact contact2 = new Contact("Jane", "Smith", "+1-555-987-6543", "456 Oak Avenue, Chicago, IL, 60610");
        mockContacts.add(contact1);
        mockContacts.add(contact2);
        when(DBManager.readContactsFromFile()).thenReturn(mockContacts);
        String result = contactsController.getContacts();
        String expected = "1. "+ contact1.toString() + "<br>"+
                            "2. "+contact2.toString() + "<br>";
        assertEquals(expected, result);
    }

    @Test
    public void testGetContactsEmpty() {
        List<Contact> mockContacts = new ArrayList<>();
        when(DBManager.readContactsFromFile()).thenReturn(mockContacts);
        String result = contactsController.getContacts();
        assertEquals("Contacts book is empty.", result);
    }

    @Test
    public void testGetContactsNull() {
        when(DBManager.readContactsFromFile()).thenReturn(null);
        String result = contactsController.getContacts();
        assertEquals("failed to load contacts", result);
    }
}
