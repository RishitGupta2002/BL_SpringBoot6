package com.example.AddressBookSpring.controller;

import com.example.AddressBookSpring.dto.ContactDTO;
import com.example.AddressBookSpring.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    // GET All Contacts
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        return ResponseEntity.ok(service.getAllContacts());
    }

    // GET Contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        Optional<ContactDTO> contact = service.getContactById(id);
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - Add New Contact
    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok(service.addContact(contactDTO));
    }

    // PUT - Update Contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContact = service.updateContact(id, contactDTO);
        return updatedContact != null ? ResponseEntity.ok(updatedContact)
                : ResponseEntity.notFound().build();
    }

    // DELETE - Remove Contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        return service.deleteContact(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}