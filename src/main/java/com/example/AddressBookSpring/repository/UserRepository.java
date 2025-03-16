package com.example.AddressBookSpring.repository;

import com.example.AddressBookSpring.model.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<UserInfo, Long> {

    public UserInfo findByUsername(String username);
}