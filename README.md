# BudapestGo2

BudapestGo2 is a pet project which meant to implement every relevant features of a trip planner which depends on public transport.
(The 'el-proyecte-grande-sprint-1-java-Kieferer' repository only contains the backend part of the project.)

- [Description for the project](#dockerized-version-can-be-pulled-from-DockerHub)
    - [Section 2 - Used technologies](#section-2---Used-technologies)
    - [Section 3 - Features](#section-3---Features)

#Dockerized version can be pulled from DockerHub:
https://hub.docker.com/repository/docker/kieferer/budapestgo2/general

The application has a site for 'customers' which passangers can use to get information about routes, stops and also able to manage their tickets and passes. BudapestGo2 built in a way that its not relay on system administrators or programmers for maintaining and updating content and some specific kind of aspects which gives flexibility and reduces causes to touch the code. This achieved by an 'employee' site where authorized users can register new and modify existing routes, stops, passes and articles with images.

##Used technologies:

 Backend
  - Java,
  - Spring Boot, Spring Security, JWT
  - JPA Hibernate, 
  - Lombok
  - Google OAuth2
 Frontend
  - JavaScript
  - React
  - Leaflet
 Database
  - PostgreSQL
  - Docker, DockerHUB
  Others
  - CI/CD GitHub workflows

##Features

User Registration and Authentication: Users can create accounts and securely log in to the platform to ask and answer questions. On the other hand for the employees the admin registrates them into the database.

Route Search Functionality: The users can search for routes and stops and check which routes go through on the selected stop. The employees can create and modify both the routes and stops as well.

Pass Buy Functionality: Users not just be able to buy different type of passes but they can also check their active and expired passes. The employees can create and modify passes. 

News Section: Users can read and select news for more details on the homepage. The employees can create and modify news. 

##Getting Started

Follow these instructions to get a copy of the BudapestGo2 project up and running on your local machine for development and testing purposes.

##Prerequisites

 * Java Development Kit (JDK)
 * Spring Framework
 * JDBC Database (e.g., PostgreSQL)
 * Any required dependencies as specified in the project configuration

Installation

Clone the repository: git clone https://github.com/CodecoolGlobal/el-proyecte-grande-sprint-1-java-Kieferer
Configure the project with the necessary environment variables and database connection details.
Build and run the project using your preferred development environment or command-line tools.
Access the application through the provided URL and create your account to start asking and answering questions.


  

  