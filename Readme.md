# Prism Database

This project consists of a server which connects to a MySQL database and a client which accesses said database through remote execution of server methods via the RMI library.

## Overview

The project consists of two independent modules: a server module named “Backend” and a client module named “Client”. Each module has its own Maven pom.xml file describing its internal dependencies and build configuration. 

### Backend package

The server side of the project consists of the following components:

#### Interfaces

A People interface which defines the methods required by the program to handle all the data to be included in the database.

A Prism interface which defines the methods which will be accessible to the client via RMI to access and manage the database.

#### Handlers

A Database class which implements all the methods which interact directly with the MySQL database.

A PrismImp which implements the methods from the Prism interface and will be the implementation used remotely by the client. Receives data from the client and formats it for its use on the Database class.

#### Entities

A Person class which implements the methods from the People interface and holds data for each instance of a person to be stored in the database.

#### Point of entry

The Server class contains the main method of the program and is in charge of initiating the RMI registry, instantiating the PrismImp class and listening for remote calls.

### Client package

The client consists of the following components: 

##### Interfaces

Same interfaces as the server

#### Main Classes

A ClientApp class that handles the communication with the server and the main methods.

A ConsoleMenu class that provides an interface through console to print the data and interact with it.


## Requisites fulfilled

All basic functionality works as intended. Users can add people to the database, delete people from it and search the database using any combination of parameters desired.

The extra functionality implemented includes the possibility of updating a previously added person in the database and the database itself to serve as a persistence method. Since the clients do not hold any data, RMI callbacks have not been deemed necessary.


## Getting Started


These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. This project is not intended to be deployed on a live production system. 

### Prerequisites

MySQL and a Java VM are required to run this application.

### Installation and deployment for testing

This project has been built with the IntelliJ IDEA Ultimate IDE and using Maven as a dependency manager.

The project consists of two independent modules: one for the client and one for the server.

To compile the full project, use the maven build command on both modules.

Make sure to have the server running before executing the client. 

## Built With

* [IntelliJ](https://www.jetbrains.com/idea/) - The IDE used
* [Maven](https://maven.apache.org/) - Dependency Management
* [MySQL](https://www.mysql.com/) - Used to manage the database 

## Authors

* **Pablo Beltrán de Casso** - *Product Owner and Developer* - [p.beltran-PO](https://redes.ls.fi.upm.es/p.beltran-PO)

* **Ignacio de las Alas-Pumariño Martínez** - *SCRUM Master and Developer*- [i.dmartinez-SM](https://redes.ls.fi.upm.es/i.dmartinez-SM)

 




