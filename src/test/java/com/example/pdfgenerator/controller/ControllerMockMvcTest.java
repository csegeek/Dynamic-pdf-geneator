 package com.example.pdfgenerator.controller;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.pdfgenerator.entity.Invoice;
import com.example.pdfgenerator.entity.Item;
import com.example.pdfgenerator.service.PdfGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.source.ByteArrayOutputStream;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class ControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PdfGeneratorService pdfGeneratorService;

    @InjectMocks
    private PdfController pdfController;

    private Invoice invoice;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pdfController).build();

        invoice = new Invoice();
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
    }

    @Test
    public void testGeneratePdf() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.assignBytes(new ObjectMapper().writeValueAsBytes(invoice));
       Mockito.when(pdfGeneratorService.generateByteArray(any(Invoice.class))).thenReturn(outputStream);

        mockMvc.perform(MockMvcRequestBuilders.post("/generate-pdf")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invoice)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(MockMvcResultMatchers.content().bytes(outputStream.toByteArray()));
    }
}
