package com.airbnb.Controller;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.FavouriteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favourite")
public class FavouriteController {

    private FavouriteRepository favouriteRepository;

    public FavouriteController(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

@PostMapping("/addFavourite")
    public ResponseEntity<?> IsFavourite(@RequestBody Favourite favourite,
                                         @AuthenticationPrincipal PropertyUser propertyUser){
favourite.setPropertyUser(propertyUser);
        Favourite saved = favouriteRepository.save(favourite);


        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }




}
