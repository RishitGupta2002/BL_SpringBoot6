package com.example.AddressBookSpring.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationMessage {

    private String username;
    private String email;


}