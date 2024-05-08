package com.airbnb.service;
import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import com.sun.deploy.panel.IProperty;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private PropertyUserRepository userRepository;
    private JWTservice jwtservice;

    public UserService(PropertyUserRepository userRepository, JWTservice jwtservice) {
        this.userRepository = userRepository;
        this.jwtservice = jwtservice;
    }

    public PropertyUser addUser(PropertyUserDto propertyUserDto) {

        PropertyUser user = new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUsername(propertyUserDto.getUsername());
        user.setEmail(propertyUserDto.getEmail());
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(), BCrypt.gensalt(10)));
        user.setUserRole("ROLE_USER");


        PropertyUser savedUser = userRepository.save(user);
        return savedUser;
    }


    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> optionalUser = userRepository.findByUsername(loginDto.getUsername());
        if (optionalUser.isPresent()) {
            PropertyUser propertyUser = optionalUser.get();
            if (BCrypt.checkpw(loginDto.getPassword(), propertyUser.getPassword())) {
                return jwtservice.generateToken(propertyUser);
            }
        }

        return null;


    }
}



