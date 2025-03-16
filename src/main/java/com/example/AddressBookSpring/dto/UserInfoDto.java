package com.example.AddressBookSpring.dto;

import com.example.AddressBookSpring.model.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto  extends UserInfo {

    private String userName;

    private String lastName;
    private long phoneNumber;
    private String email;

}