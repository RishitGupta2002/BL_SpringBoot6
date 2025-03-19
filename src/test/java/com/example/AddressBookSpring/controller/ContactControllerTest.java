package com.example.AddressBookSpring.controller;

import com.example.AddressBookSpring.dto.ContactDTO;
import com.example.AddressBookSpring.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @Test
    public void testGetAllContacts_ReturnsOkAndContacts() {
        // Arrange
        ContactDTO contact1 = new ContactDTO(1L, "John Doe", "123-456-7890", "john.doe@example.com", "john street");
        ContactDTO contact2 = new ContactDTO(2L, "Jane Doe", "987-654-3210", "jane.doe@example.com", "jane street");
        List<ContactDTO> contacts = Arrays.asList(contact1, contact2);
        when(contactService.getAllContacts()).thenReturn(contacts);

        // Act
        ResponseEntity<List<ContactDTO>> response = contactController.getAllContacts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(contacts, response.getBody());
    }


    @Test
    public void testGetContactById_WithExistingId_ReturnsOkAndContact() {
        // Arrange
        ContactDTO contact = new ContactDTO(1L, "John Doe", "123-456-7890", "john.doe@example.com", "john street");
        when(contactService.getContactById(1L)).thenReturn(Optional.of(contact));

        // Act
        ResponseEntity<ContactDTO> response = contactController.getContactById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(contact, response.getBody());
    }

    @Test
    public void testGetContactById_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        when(contactService.getContactById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ContactDTO> response = contactController.getContactById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddContact_ReturnsOkAndNewContact() {
        // Arrange
        ContactDTO newContact = new ContactDTO(null, "New User", "987-654-3210", "new.user@example.com", "new street");
        ContactDTO savedContact = new ContactDTO(1L, "New User", "987-654-3210", "new.user@example.com", "new street");
        when(contactService.addContact(any(ContactDTO.class))).thenReturn(savedContact);

        // Act
        ResponseEntity<ContactDTO> response = contactController.addContact(newContact);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedContact, response.getBody());
    }

    @Test
    public void testUpdateContact_WithExistingId_ReturnsOkAndUpdatedContact() {
        // Arrange
        Long contactId = 1L;
        ContactDTO existingContact = new ContactDTO(contactId, "Old Name", "123-456-7890", "old.name@example.com", "old street");
        ContactDTO updatedContactDTO = new ContactDTO(contactId, "Updated Name", "987-654-3210", "updated.name@example.com", "updated street");
        when(contactService.updateContact(contactId, updatedContactDTO)).thenReturn(updatedContactDTO);

        // Act
        ResponseEntity<ContactDTO> response = contactController.updateContact(contactId, updatedContactDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedContactDTO, response.getBody());
    }

    @Test
    public void testUpdateContact_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        Long contactId = 99L;
        ContactDTO updatedContactDTO = new ContactDTO(contactId, "Updated Name", "987-654-3210", "updated.name@example.com", "updated street");
        when(contactService.updateContact(contactId, updatedContactDTO)).thenReturn(null);

        // Act
        ResponseEntity<ContactDTO> response = contactController.updateContact(contactId, updatedContactDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteContact_WithExistingId_ReturnsNoContent() {
        // Arrange
        Long contactId = 1L;
        when(contactService.deleteContact(contactId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = contactController.deleteContact(contactId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteContact_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        Long contactId = 99L;
        when(contactService.deleteContact(contactId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = contactController.deleteContact(contactId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



}