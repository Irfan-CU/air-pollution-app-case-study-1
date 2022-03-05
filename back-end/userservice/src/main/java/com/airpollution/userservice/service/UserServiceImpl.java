package com.airpollution.userservice.service;

import com.airpollution.userservice.domain.User;
import com.airpollution.userservice.exception.UserAlreadyExistException;
import com.airpollution.userservice.exception.UserNotFoundException;
import com.airpollution.userservice.repository.UserRepository;
import com.airpollution.userservice.userdto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * This class is implementing the UserService interface. This class has to be annotated with
 * @Service annotation.
 * @Service indicates annotated class is a service
 * which hold business logic in the Service layer
 *
 * */
@Service
public class UserServiceImpl implements UserService {

    BCryptPasswordEncoder bCryptPasswordEncoder;
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    /**
     * To get the property values
     */
    @Value("${app.service.message1}")
    private String message1;

    @Value("${app.service.message2}")
    private String message2;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.restTemplate=restTemplate;
    }

    public UserServiceImpl() {
    }

    @Override
    public UserDto saveUser(UserDto userDto) throws UserAlreadyExistException {

        ModelMapper mapper= new ModelMapper();
        User userEntity= mapper.map(userDto, User.class);
        System.out.println("inside saveuser; userEntity: " + userEntity.toString());
        Optional<User> userResult = userRepository.findById(userEntity.getId());

        if (userResult.isPresent()) {
            throw new UserAlreadyExistException(message1);
        }

        userEntity.setId(userDto.getId());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        System.out.println("inside saveuser; userEntity: " + userEntity.toString());
        userRepository.save(userEntity);
        System.out.println("inside saveuser after; userEntity: " + userEntity.toString());
        return userDto;
    }
    @Override
    public User findByIdAndPassword(String id, String password) throws UserNotFoundException {
        User authUser = userRepository.findByIdAndPassword(id, password);
        if (authUser == null) {
            throw new UserNotFoundException(message2);
        }
        return authUser;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email)
    {
        Optional userOptional = userRepository.findById(email);
        if(!userOptional.isPresent())
            throw new UsernameNotFoundException(email);
        return new ModelMapper().map((User) userOptional.get(), UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("inside loadUserByUsername");
        User user = userRepository.findById(s).get();

        UserBuilder builder;

        if (user != null) {
            System.out.println("inside loadUserByUsername AND user found: " + user.toString());
            builder = org.springframework.security.core.userdetails.User.withUsername(s);
//            builder.password(bCryptPasswordEncoder.encode(user.getPassword()));
            builder.password(user.getPassword());
            builder.roles("USER");
            System.out.println("done building builder: " + builder.toString());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        System.out.println("about to leave builder!");

        return builder.build();
    }

    @Override
    public void initiateFavoriteList(String userEmail) {
        System.out.println("inside getAirQualityDataForFavCities");
        String url = "http://cgiair-favorite/fav/newuser/" + userEmail;
        restTemplate.exchange(url, HttpMethod.POST, null, Void.class);
    }

    @Override
    public Boolean userExist(String email)
    {
        if(userRepository.findById(email).isPresent()) {
            System.out.println("user existence is "+"true");
            return true;
        }
        else {
            System.out.println("user existence is "+"false");
            return false;
        }
    }

    @Override
    public boolean addOrUpdateProfilePic(String userEmail, byte[] image) {
        Optional userOptional = userRepository.findById(userEmail);

        if (userOptional.isPresent()) {
            User user = (User) userOptional.get();
            user.setProfilePic(image);
            userRepository.save(user);
            return true;
        } return false;
    }

    @Override
    public byte[] getProfilePic(String userEmail) {
        Optional userOptional = userRepository.findById(userEmail);

        if (userOptional.isPresent()) {
            return ((User) userOptional.get()).getProfilePic();
        }
        return null;
    }
}
