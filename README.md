# Football Manager Application

Football Manager Application is a RESTful API application designed to manage football teams and players. The project provides CRUD functionality for teams and players, along with additional features like transferring players between teams with calculated transfer costs.


## Getting Started

1. Configure the application:
   - Open the `runApplication.bat` file in the root directory.
   - Replace the placeholders with your actual database credentials and JWT secret:
     ```bat
     @echo off

     set DB_URL=<your-url>
     set DB_USERNAME=<your-username>
     set DB_PASSWORD=<your-password>

     java -jar target/football-manager-0.0.1-SNAPSHOT.jar
     ```

2. Open a terminal or command prompt and navigate to the project's root directory (where the pom.xml file is located). Then build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   runApplication.bat
   ```
4. Explore the API:
   - The Postman collection for the application's API is available in the file ```football-manager-api.postman_collection.json``` located in the root directory of the repository.

