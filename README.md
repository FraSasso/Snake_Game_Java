# Snake_Game_Java

This Java-based web application provides a system for managing a simple game. 
It includes functionality for user authentication, game score recording, and displaying top scores. 
The project uses the Spark Framework for server-side routing, Handlebars templates for rendering dynamic web pages, and MySQL for data storage. The application prioritizes security and error handling through custom route interfaces and robust database interactions.

# Compilation

  - Set Up the Database by creating the 'user' and 'games' tables in MySQL
  - Configure Database Credentials --> return DriverManager.getConnection("jdbc:mysql://localhost/SimpleDB", "root", "root");

# Run the Application
  - Use IntelliJ IDEA or a terminal to execute the Main.java file 
  - Access it by opening a browser and going to http://localhost:4567/
