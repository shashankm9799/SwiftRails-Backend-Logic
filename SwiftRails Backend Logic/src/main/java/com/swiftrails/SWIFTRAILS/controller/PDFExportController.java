package com.swiftrails.SWIFTRAILS.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.swiftrails.SWIFTRAILS.services.PDFGeneratorService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PDFExportController {
	@Autowired
	private PDFGeneratorService pdfGeneratorService;
	
	@GetMapping("/generatePDF")
    public void generatePDF(HttpServletResponse response,
                            @RequestParam String mailId,
                            @RequestParam String cardNumber,
                            @RequestParam String cardExpiry,
                            @RequestParam String cardCVV,
                            @RequestParam double amount,
                            @RequestParam String name,
                            @RequestParam String trainName,
                            @RequestParam String date,
                            @RequestParam String from_station,
                            @RequestParam String to_station,
                            @RequestParam String seat) throws IOException {

        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = "pdf_" + currentDateTime + ".pdf";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        // Call the PDFGeneratorService to generate the PDF with the provided parameters
        byte[] pdfContent = pdfGeneratorService.export(mailId, cardNumber,  cardExpiry,  cardCVV,  amount,
                 name,  trainName,  date, seat, from_station,
                 to_station);

        // Write the PDF content to the response output stream
        response.getOutputStream().write(pdfContent);
    }
	
}
