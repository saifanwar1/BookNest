package com.airbnb.Controller;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Review")
public class ReviewController {

    private ReviewRepository reviewRepository;
private PropertyRepository propertyRepository;
    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @RequestMapping("/{propertyId}")
    public ResponseEntity<String> Addreview(@PathVariable long propertyId,
                                            @RequestBody Review review,
                                            @AuthenticationPrincipal PropertyUser user){

        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        Property property = optionalProperty.get();

        Review r = reviewRepository.findReviewByUser(property, user);
       if (r!=null){
           return new ResponseEntity<>("you already added a review for this property",HttpStatus.BAD_REQUEST);
       }

        review.setProperty(property);
        review.setPropertyUser(user);

        reviewRepository.save(review);

        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }
@GetMapping("/userReviews")
public ResponseEntity<List<Review>> getUserReviews(@AuthenticationPrincipal PropertyUser user){

    List<Review> reviews = reviewRepository.findByPropertyUser(user);
    return new ResponseEntity<>(reviews,HttpStatus.OK);

}



}
