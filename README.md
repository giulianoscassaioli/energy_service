# Energy Service app

## Table of Contents

- [Introduction and technology stack](#Introduction-and-Technologies-stack)
- [Versions](#Version)
- [Remote API](#Remote-API)
- [Main Features implemented](#Main-Features-implemented)
- [Input](#Input)
- [Testing](#Testing)
- [Features in backlog](#Feature-In-Backlog)
- [External Resources](#External-resources)
- [License](#License)

## Introduction and Technologies stack
This is a CRM application to manage Clients and their Invoces.
Every User registered can Login or can SignUp if they are new to the service,
then they can view the list of all the Clients
and their invoces and have different ways of looking for them within the application.
Loging in with the Username "admin" and password "admin" gives the User extra-privileges
such us deleting a client or an invoice, updating them or saving new ones in the system.

### Tech stack: 
- Java + Spring Boot
- Authentication through Jason Web Token for the API RESTfull service
- Authentication for the Web Service (including Login and Signup)
- Secure storage of the password using encryption
- Support of Pagination
- Auto-calculation of Clients total income based on their Invoces
- JUnit
- Swagger Documentation
- Eclipse
- Postgress DBMS
- Thymeleaf for the Web Service (Front-End)
- Advanced REST Client
- Maven
- Git and Gihub, of course : ) 

## Introduction and Technologies stack

Latest stable version: 1.0  


## Main Features implemented

- Web service
- Input/Output via REST API

## Input / Output

For the submission of a this app I decided to implement a REST interface for sending data. The output of the service will be the printing of the Client.

SIGN-UP

You can use The Rest service with Post Man.
You can Sign-Up to the application at the link https://customers-management-service.herokuapp.com/auth/signup
sending in the Body of the POST Request a Json like this:

```
{
  "username": "yourUsername",
  "email": "yourUsername",
  "role": [
    "user"
  ],
  "password": "yourPassword",
  "nome": "yourName",
  "cognome": "yourSurname"
}

```
You can also choose your role if you make an admin account you will have extra-priveleges.

LOGIN

If you want to login now using your credentials simply make a post request to https://customers-management-service.herokuapp.com/auth/login
sending in the Body of the Request a Json like this:

```
 {
  "userName": "yourUsername",
  "password": "yourPassword"
}
```
You will have a response status code 200 and a response Bearer-Token to keep
in order to have permission of using any function of this application by putting it
into the headers section before making any request.
From now on in your Headers section of any request choose in the field KEY->Authorization
and in The field VALUE write Bearer then a space and the the token you received in your login response.

SAVE A CLIENT

URL: https://customers-management-service.herokuapp.com/api/salvacliente
Examples of JSON to insert into the BODY of the POST Request to save a new Client:

 ``` 
 {
  "id":0,
  "ragioneSociale": "clientsName",
  "partitaIva": "client's VAT",
  "tipoCliente": "SRL",
  "email": "email@email.com",
  "pec": "pec@pec.com",
  "telefono": "06544555",
  "nomeContatto": "nameOfTheContact",
  "cognomeContatto": "surnameOfTheContact",
  "telefonoContatto": "phneOfTheContact",
  "emailContatto": "emailOfTheContact",
  "indirizzoSedeOperativa": {
    "id": 0,
    "via": "address",
    "civico": numberOfTheAddress,
    "cap": "zipCode",
    "localita": "location type",
    "comune": {
      "id": 1,
      "nome": "name of the city",
      "provincia": {
        "id": 97,
        "nome": "name of the province",
        "sigla": "Acronym of the province",
        "regione": "string"
      }
    }
  },
  "indirizzoSedeLegale": {
   "id": 0,
    "via": "address",
    "civico": numberOfTheAddress,
    "cap": "zipCode",
    "localita": "location type",
    "comune": {
      "id": 1,
      "nome": "name of the city",
      "provincia": {
        "id": 97,
        "nome": "name of the province",
        "sigla": "Acronym of the province",
        "regione": "string"
      }
    }
  },
  "dataInserimento": "2022-03-15",
  "dataUltimoContatto": "2022-03-15",
  "fatturatoTotale": 0
  
}
```


DELETE A CLIENT BY HIS ID NUMBER

URL: https://customers-management-service.herokuapp.com/api/eliminacliente/{}

You can also delete a client off the system by using this url and replacing the {} with the client's
id numeber you wish to delete

VIEW THE LIST OF ALL CLIENTS

URL: https://customers-management-service.herokuapp.com/api/trovatutticlienti
Just send a GET Request to this URL to see the list of all the clients in the system

OTHER POSSIBLE OPERATIONS

You can do the same thing did with clients by using a different End-Point
and different Json.
For Example you can add a new Invoice by sending a POST Request to

URL: https://customers-management-service.herokuapp.com/api/salvafattura

Json:

```
{
  "id": 0,
  "cliente": {
    "id": 3,
    "ragioneSociale": "string",
    "partitaIva": "string",
    "tipoCliente": "SRL",
    "email": "string",
    "pec": "string",
    "telefono": "string",
    "nomeContatto": "string",
    "cognomeContatto": "string",
    "telefonoContatto": "string",
    "emailContatto": "string",
    "indirizzoSedeOperativa": {
      "id": 0,
      "via": "string",
      "civico": 0,
      "cap": "string",
      "localita": "string"
      }
    },
    "indirizzoSedeLegale": {
      "id": 0,
      "via": "string",
      "civico": 0,
      "cap": "string",
      "localita": "string",
      "comune": {
        "id": 0,
        "nome": "string"
      }
    },
    "dataInserimento": "2022-03-12",
    "dataUltimoContatto": "2022-03-12",
    "fatturatoTotale": 0,
  "dataFattura": "2022-03-12",
  "numero": 0,
  "anno": 0,
  "importo": 0,
  "statoFattura": "string"
}
```
Specifing an existing "ragioneSociale" previuosly saved in the system.


You can delete an invoice sendig a DELETE Request at the end-point
URL: https://customers-management-service.herokuapp.com/api/eliminafattura/{}

just replace the {} with the invoice's
id numeber you wish to delete.

To view all the list of the invoices simply send a GET request at
URL: https://customers-management-service.herokuapp.com/api/allfatture

The application has many more functions as well!

For having a list of all the functions of my Rest API Email me at giulgab87@gmail.com.

## Testing

For the test I used Junit, testing all the critical modules of the system, both with unit tests on low level components and with black box tests of high level. 

## Features in backlog

- Front end in Thymeleaf
- Authentication flow


## External Resources
IDE:  
- Eclipse

Web Service Link:  
- https://customers-management-service.herokuapp.com/

Swagger Link:  
- https://customers-management-service.herokuapp.com/swagger-ui.html


Java:  
- https://spring.io/

## License
MIT ©