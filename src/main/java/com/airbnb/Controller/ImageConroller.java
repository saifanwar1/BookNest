package com.airbnb.Controller;

import com.airbnb.entity.Images;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.ImagesRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageConroller {

private ImagesRepository imagesRepository;
private PropertyRepository propertyRepository;
private BucketService bucketService;

    public ImageConroller(ImagesRepository imagesRepository, PropertyRepository propertyRepository, BucketService bucketService) {
        this.imagesRepository = imagesRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(     @RequestParam MultipartFile file,
                                             @PathVariable String bucketName,
                                             @PathVariable long propertyId,
                                             @AuthenticationPrincipal PropertyUser propertyuser) {
        String imageUrl = bucketService.uploadFile(file, bucketName);
        Property property = propertyRepository.findById(propertyId).get();

        Images img = new Images();
        img.setImagesUrl(imageUrl);
        img.setProperty(property);
        img.setPropertyUser(propertyuser);

        Images savedImage = imagesRepository.save(img);

        return new ResponseEntity<>(savedImage, HttpStatus.OK);
    }


}
