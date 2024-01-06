# Notes API

**A RESTful API for managing notes, built with Spring Boot and JWT authentication.**

## Features

- **User Registration and Login**
- **Secured Note Management Endpoints**
    - Create new notes
    - Retrieve all notes for a user
    - Retrieve a specific note by ID
    - Update existing notes
    - Delete notes
    - Search notes by keyword

## Technologies

- **Spring Boot**
- **Spring Security**
- **JWT Authentication**
- **MongoDB** 
- **Java** 

## Getting Started

1. **Prerequisites:**
    - Java (17)
    - Maven 
    - MongoDB 

2. **Clone the Repository:**
    ```bash
    git clone [https://github.com/sanjotsingh10/restapi.git](https://github.com/sanjotsingh10/restapi.git)
    ```

3. **Run the Application:**
    ```bash
    cd restapi
    mvn spring-boot:run
    ```

4. **Access the API:**
    The API will be accessible at `http://localhost:8080` (or specify the port).

## API Endpoints

**Authentication:**

- `POST /api/auth/signup` (register a new user)
- `POST /api/auth/login` (authenticate a user and obtain a JWT token)

**Notes (requires authentication - Add token recieved through login to header in "Authorization" field as "Bearer <token>"):**

- `POST /api/notes` (create a new note)
- `GET /api/notes` (get all notes for the authenticated user)
- `GET /api/notes/{id}` (get a specific note by ID)
- `PUT /api/notes/{id}` (update an existing note)
- `DELETE /api/notes/{id}` (delete a note)
- `GET /api/notes/search/{keyword}` (search notes by keyword)
