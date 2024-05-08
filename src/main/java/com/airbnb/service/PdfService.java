package com.airbnb.service;

import com.airbnb.dto.BookingDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PdfService {

    private static final String PDF_DIRECTORY = "/path/to/your/pdf/directory/";

    public boolean generatePDF(String fileName, BookingDto dto) {
        try {


            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLUE);
            Chunk bookingConfirmation = new Chunk("Booking-Confirmation", font);
            Chunk guestName = new Chunk("Guest Name:"+dto.getGuestName(), font);
            Chunk price = new Chunk("Price per Night:"+dto.getPrice(), font);
            Chunk totalPrice = new Chunk("Total Price:"+dto.getTotalPrice(), font);

            document.add(bookingConfirmation);
            document.add(new Paragraph("\n"));
            document.add(guestName);
            document.add(new Paragraph("\n"));
            document.add(price);
            document.add(new Paragraph("\n"));
            document.add(totalPrice);
            document.close();
return true;
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
        }
        return false;
        }
    }
