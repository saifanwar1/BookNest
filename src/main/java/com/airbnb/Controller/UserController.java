package com.airbnb.Controller;


import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.dto.TokenResponse;
import com.airbnb.entity.PropertyUser;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/abb")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //http://localhost:8080/api/abb/addUser
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyUserDto) {
        PropertyUser propertyUser = userService.addUser(propertyUserDto);

        if (propertyUser != null) {
            return new ResponseEntity<>("Registration is successfull", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went Wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //http://localhost:8080/api/abb/Login
    @PostMapping("/Login")
    public ResponseEntity<?> Login(@RequestBody LoginDto loginDto) {
       String token = userService.verifyLogin(loginDto);
        if(token!=null) {
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(token);
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Credentials",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/profile")
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser User){

      return User  ;

    }
    @PostMapping("/post")
    public String addpost(){

        return "done";
    }




}