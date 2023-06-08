# BudapestGo2

BudapestGo2 is a pet project which meant to implement every relevant features of a trip planner which depends on public transport. 
<font size=8> (The 'el-proyecte-grande-sprint-1-java-Kieferer' repository only contains the backend part of the project.) </font>

Dockerized version can be pulled from DockerHub:
https://hub.docker.com/repository/docker/kieferer/budapestgo2/general

The application has a site for 'customers' which passangers can use to get information about routes, stops and also able to manage their tickets and passes. BudapestGo2 built in a way that its not relay on system administrators or programmers for maintaining and updating content and some specific kind of aspects which gives flexibility and reduces causes to touch the code. This achieved by an 'employee' site where authorized users can register new and modify existing routes, stops, passes and articles with images.

 - [Description for the project](#description-for-the-project)
    - [Section 2 - Used technologies](#section-2---Used-technologies-started-with-spring-security)
      - [Newly developed API calls](#newly-developed-api-calls)
        - [Obtain one of the students](#obtain-one-of-the-students)
    - [Section 3 - Users Roles and Authorities](#section-3---users-roles-and-authorities)
    - [Section 4 - Permission Based Authentication](#section-4---permission-based-authentication)
      - [Newly developed API calls (management endpoints)](#newly-developed-api-calls-management-endpoints)
        - [Obtain all students](#obtain-all-students)
        - [Create a new student](#create-a-new-student)
        - [Update student](#update-student)
        - [Delete student](#delete-student)
    - [Section 5 - Cross Site Request Forgery](#section-5---cross-site-request-forgery)
    - [Section 6 - Form Based Authentication](#section-6---form-based-authentication)
    - [Section 7 - Database Authentication](#section-7---database-authentication)
    - [Section 8 - JSON Web Tokens](#section-8---json-web-tokens)
      - [API calls](#api-calls)
        - [Login and receive token](#login-and-receive-token)
        - [Send token with each request](#send-token-with-each-request)
  - [Status](#status)

 Used technologies:
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

Features

User Registration and Authentication: Users can create accounts and securely log in to the platform to ask and answer questions. On the other hand for the employees the admin registrates them into the database.

Route Search Functionality: The users can search for routes and stops and check which routes go through on the selected stop. The employees can create and modify both the routes and stops as well.

Pass Buy Functionality: Users not just be able to buy different type of passes but they can also check their active and expired passes. The employees can create and modify passes. 

News Section: Users can read and select news for more details on the homepage. The employees can create and modify news. 

Getting Started

Follow these instructions to get a copy of the BudapestGo2 project up and running on your local machine for development and testing purposes.

Prerequisites

 * Java Development Kit (JDK)
 * Spring Framework
 * JDBC Database (e.g., PostgreSQL)
 * Any required dependencies as specified in the project configuration

Installation

Clone the repository: git clone https://github.com/CodecoolGlobal/el-proyecte-grande-sprint-1-java-Kieferer
Configure the project with the necessary environment variables and database connection details.
Build and run the project using your preferred development environment or command-line tools.
Access the application through the provided URL and create your account to start asking and answering questions.

Contributing


  

  