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

Examples of JSON to insert into the BODY of the POST Request to save a new Client:

 ``` 
 {
  "id":0,
  "ragioneSociale": "string1",
  "partitaIva": "string1",
  "tipoCliente": "SRL",
  "email": "string@email.com",
  "pec": "string@pec.com",
  "telefono": "06544555",
  "nomeContatto": "string",
  "cognomeContatto": "string",
  "telefonoContatto": "string",
  "emailContatto": "string",
  "indirizzoSedeOperativa": {
    "id": 0,
    "via": "string",
    "civico": 0,
    "cap": "string",
    "localita": "string",
    "comune": {
      "id": 1,
      "nome": "string",
      "provincia": {
        "id": 97,
        "nome": "string",
        "sigla": "string",
        "regione": "string"
      }
    }
  },
  "indirizzoSedeLegale": {
    "id": 0,
    "via": "string",
    "civico": 0,
    "cap": "string",
    "localita": "string",
    "comune": {
      "id": 1,
      "nome": "string",
      "provincia": {
        "id": 97,
        "nome": "string",
        "sigla": "string",
        "regione": "string"
      }
    }
  },
  "dataInserimento": "2022-03-15",
  "dataUltimoContatto": "2022-03-15",
  "fatturatoTotale": 0
  
}
```
## Testing

For the test I used Junit, testing all the critical modules of the system, both with unit tests on low level components and with black box tests of high level. 

## Features in backlog

- Front end in Thymeleaf
- Authentication flow


## External Resources
IDE:  
- Eclipse

Heroku service:  
- https://www.heroku.com/


Java:  
- https://spring.io/

## License
MIT ©