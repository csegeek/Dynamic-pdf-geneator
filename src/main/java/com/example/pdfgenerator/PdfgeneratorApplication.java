package com.example.pdfgenerator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import com.example.pdfgenerator.controller.PdfController;
import com.example.pdfgenerator.entity.Invoice;
import com.example.pdfgenerator.entity.Item;
import com.example.pdfgenerator.service.PdfGeneratorService;
import com.itextpdf.io.source.ByteArrayOutputStream;

@SpringBootApplication
public class PdfgeneratorApplication implements CommandLineRunner {
     
	@Autowired
	 private  PdfGeneratorService pdfGeneratorService;

	 @Autowired
	 private PdfController pdfController;
	public static void main(String[] args) {
		SpringApplication.run(PdfgeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Invoice invoice =new Invoice();
        invoice.setSeller("XYZ Pvt. Ltd.");
        invoice.setSellerGstin("29AABBCCDD121ZD");
        invoice.setSellerAddress("New Delhi, India");
        invoice.setBuyer("Vedant Computers");
        invoice.setBuyerGstin("29AABBCCDD131ZD");
        invoice.setBuyerAddress("New Delhi, India");

        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setName("Product 1");
        item.setQuantity("12 Nos");
        item.setRate(123.00);
        item.setAmount(1476.00);
        items.add(item);
        invoice.setItems(items); 

		//ByteArrayOutputStream arrayOutputStream=pdfGeneratorService.generateByteArray(invoice);
        ResponseEntity<byte[]> output=pdfController.generatePdf(invoice);
		System.out.println(output.getStatusCode());
	}

}
