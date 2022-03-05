package com.airpollution.userservice.service;

import com.airpollution.userservice.domain.User;
import com.airpollution.userservice.exception.UserAlreadyExistException;
import com.airpollution.userservice.exception.UserNotFoundException;
import com.airpollution.userservice.userdto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void initiateFavoriteList(String userEmail);

    UserDto saveUser(UserDto user) throws UserAlreadyExistException;

    User findByIdAndPassword(String email, String password) throws UserNotFoundException;

    UserDto getUserDetailsByEmail(String email);

    Boolean userExist(String email);

    boolean addOrUpdateProfilePic(String userEmail, byte[] image);

    byte[] getProfilePic(String userEmail);
}
