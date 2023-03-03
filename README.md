
# Dynamic PDF Generation

 A Spring Boot Application with REST API to generate PDF using iText.\
Apache iText is an open-source Java library that supports the development and conversion of PDF documents. In this tutorial, we will learn how to use iText to develop Java programs that can create, convert, and manipulate PDF documents.
### Request
`POST /generate-pdf`

 `POST` 'http://localhost:8080/generate-pdf' \
`Content-Type:` application/json' 

### Body
```--data-raw 
{
"seller": "XYZ Pvt. Ltd.",
"sellerGstin": "29AABBCCDD121ZD", 
"sellerAddress": "New Delhi, India", 
"buyer": "Vedant Computers",
"buyerGstin": "29AABBCCDD131ZD",
"buyerAddress": "New Delhi, India", 
"items": [
{
"name": "Product 1", 
"quantity": "12 Nos", 
"rate": 123.00,
"amount": 1476.00 
},
{
"name": "Product 2", 
"quantity": "12 Nos", 
"rate": 123.00,
"amount": 1476.00 
},
{
"name": "Product 3", 
"quantity": "12 Nos", 
"rate": 123.00,
"amount": 1476.00 
},
{
"name": "Product 4", 
"quantity": "12 Nos", 
"rate": 123.00,
"amount": 1476.00 
}
] 
}
```
### Response

![Response](Response.JPG?text=Response+Pdf+Here)


#### To save the generated pdf page in local storage using postman click on `save Response -> save as file` or send a get request using `send and download` button in postman.