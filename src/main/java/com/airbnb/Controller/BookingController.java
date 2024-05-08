package com.airbnb.Controller;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.BucketService;
import com.airbnb.service.PdfService;
import com.airbnb.service.SmsService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;

    private final PdfService pdfService;
    private final BucketService bucketService;

    private SmsService smsService;

    public BookingController(BookingRepository bookingRepository,
                             PropertyRepository propertyRepository, PdfService pdfService, BucketService bucketService, SmsService smsService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
        this.smsService = smsService;
    }

    @PostMapping("/createBooking/{propertyId}")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking,
                                           @AuthenticationPrincipal PropertyUser propertyUser,
                                           @PathVariable long propertyId) throws IOException {

        booking.setPropertyUser(propertyUser);
        Property property = propertyRepository.findById(propertyId).get();
        Integer propetyprice = property.getNightly_price();

        Integer totalNights = booking.getTotalNights();
        int totalPrice = propetyprice * totalNights;
        booking.setProperty(property);
        booking.setTotalPrice(totalPrice);

        Booking createdBooking = bookingRepository.save(booking);

        BookingDto dto = new BookingDto();

        dto.setBookingId(createdBooking.getId());
        dto.setGuestName(createdBooking.getGuestName());
        dto.setPrice(propetyprice);
        dto.setTotalPrice(createdBooking.getTotalPrice());
        //create PDF with Booking confirmation


        boolean b = pdfService.generatePDF("D://bnb-pdf//" + "Boking-Confirmation-id" + createdBooking.getId() + ".pdf", dto);
        if (b) {
            //upload your  file in to bucket
            MultipartFile file = BookingController.createMultipartFile("D://bnb-pdf//" + "Boking-Confirmation-id" + createdBooking.getId() + ".pdf");
            String uploadFileUrl = bucketService.uploadFile(file, "my-airbnb1");
            smsService.sendSms("+91 7979767819","your booking is confirmed.click for more information:"+uploadFileUrl);

        } else {
            return new ResponseEntity<>("Something went wrong", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    public static MultipartFile createMultipartFile(String filePath) throws IOException {
        // Create a File object from the provided file path
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }

        // Read the content of the file into a byte array
        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Create a ByteArrayResource with the file content
        Resource resource = new ByteArrayResource(fileContent);

        // Convert to Multipart file
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return resource.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(), fileContent);

            }
        };

        // Return the created MultipartFile object
        return multipartFile;
    }
}