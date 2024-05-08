package com.airbnb.Controller;

import com.airbnb.entity.Property;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.PropertyUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {


private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("{locationName}")
    public ResponseEntity<List<Property>> findProperty(@PathVariable String locationName){
        List<Property> properties = propertyRepository.findPropertyBylocation(locationName);
return  new ResponseEntity<>(properties, HttpStatus.OK);
    }
    @GetMapping("{location}")
    public ResponseEntity<List<Property>> findPropertyview(@PathVariable String location) {

        List<Property> properties = propertyRepository.findPropertyBylocation(location);
        return new ResponseEntity<>(properties, HttpStatus.OK);

    }


}
