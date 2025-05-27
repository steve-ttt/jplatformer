# Java Platformer Game (gamelib)

This project is a simple platformer game developed in Java using Swing for the UI. It has been structured as a Maven project.

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Apache Maven

## How to Build

1.  Clone the repository or download the source code.
2.  Open a terminal or command prompt in the project's root directory (where `pom.xml` is located).
3.  Run the following Maven command to compile the code, run tests, and package it into a JAR file:

    ```bash
    mvn clean install
    ```
    This will create a JAR file in the `target/` directory (e.g., `target/gamelib-1.0-SNAPSHOT.jar`).

## How to Run

After successfully building the project, you can run the game using the following command from the project's root directory:

```bash
java -jar target/gamelib-1.0-SNAPSHOT.jar
```

This will launch the game window.

## Project Structure

The project follows Maven's Standard Directory Layout:
- `src/main/java`: Contains the main source code for the game.
- `src/main/resources`: Contains resources for the main application (currently unused).
- `src/test/java`: Contains JUnit tests for the game.
- `src/test/resources`: Contains resources for the tests (currently unused).
- `pom.xml`: The Project Object Model file for Maven, defining project configuration, dependencies, and build settings.
