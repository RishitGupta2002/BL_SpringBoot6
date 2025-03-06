package com.example.AddressBookSpring.service;

import com.example.AddressBookSpring.dto.ContactDTO;

import java.util.List;

public interface IContactService {
    List<ContactDTO> getAllContacts();
    ContactDTO getContactById(Long id);
    ContactDTO createContact(ContactDTO contactDTO);
    ContactDTO updateContact(Long id, ContactDTO contactDTO);
    void deleteContact(Long id);
}