# Java Console Game Platform

![Language](https://img.shields.io/badge/language-Java-B07219?style=flat-square)
![Platform](https://img.shields.io/badge/platform-Console%20(CLI)-lightgrey?style=flat-square)
![IDE](https://img.shields.io/badge/IDE-Eclipse-2C2255?style=flat-square)

A console-based application in Java that simulates a video game platform. It features a role-based access control system (Player, Tester, Admin) and handles data persistence through file I/O (CSV and TXT).

## Table of Contents

- [About The Project](#about-the-project)
- [Key Features](#key-features)
- [Built With](#built-with)
- [Getting Started](#getting-started)
- [Technical Deep Dive](#technical-deep-dive)
- [Future Improvements](#future-improvements)
- [License](#license)

## About The Project

This project is a command-line application built to demonstrate core Java principles, including Object-Oriented Programming, file-based data persistence, and role-based user management. It simulates a simple video game platform where users can interact based on their assigned roles.

## Key Features

-   **Role-Based Access Control**: Four distinct user roles (Player, Tester, Admin, Guest) with different permissions.
-   **User Authentication & Session Persistence**: A complete login/registration system that saves user data to a `members.txt` file between sessions.
-   **Game Management & Review System**: Users can add games, view the catalog, and leave reviews (likes/dislikes, comments).
-   **Admin-level Moderation Tools**: Administrators can handle user reports, block/unblock users, and manage content.
-   **Data Persistence**: Reads the initial game list from a `ListeJeux.csv` file and persists user data locally.

## Built With

-   **Java** (Java 11+ recommended)
-   **Eclipse IDE**

## Getting Started

To get a local copy up and running, follow these steps.

1.  **Prerequisites**: Ensure you have a Java Development Kit (JDK) installed on your machine.
2.  **Clone the repository:**
    ```sh
    git clone https://github.com/nico916/plateforme-jeux.git 
    # Remember to update this URL if you rename the repository!
    ```
3.  **Compile and Run from the Command Line:**
    Navigate to the `src` directory and run the following commands:
    ```sh
    # Compile all Java files
    javac et3projet/*.java
    
    # Run the main application
    java et3projet.Plateforme
    ```

## Technical Deep Dive

-   **Object-Oriented Design**: The application is structured around clear classes like `Membre`, `Jeu`, and `Evaluation`, encapsulating data and behavior according to OOP principles.
-   **Data Structures**: Core logic relies on Java's built-in collections. A `HashMap` is used for efficient user lookup by username (O(1) average time complexity), while an `ArrayList` stores the list of games.
-   **File I/O**: Data persistence is handled manually through custom methods that read from CSV files and write user data to a structured TXT file, demonstrating a solid understanding of Java's I/O streams.

## Future Improvements

-   **Migrate to a GUI Framework**: Rebuild the user interface with a graphical framework like JavaFX or Swing.
-   **Refactor to a Strict MVC Architecture**: Further separate the data logic (Model), UI (View), and user input handling (Controller).
-   **Implement a Relational Database**: Replace the file-based storage with a more robust database system like SQLite or PostgreSQL.
-   **Add Integrated Mini-Games**: Allow users to actually play simple games within the platform.

## License

Distributed under the MIT License. See `LICENSE` file for more information.
