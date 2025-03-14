
# Brick Breaker with Power-Ups

Brick Breaker with Power-Ups is a JavaFX-based arcade game where players control a paddle to bounce a ball and break bricks. The game features multiple levels, increasing difficulty, and random power-ups that expand the paddle. This project is a great example of using JavaFX for game development.

## Features

- **Classic Brick Breaker Gameplay:**  
  Control a paddle to bounce a ball, break bricks, and score points.

- **Dynamic Levels:**  
  As you clear bricks, the game increases in level, speeding up the ball and adding more rows of bricks.

- **Power-Ups:**  
  Random power-ups occasionally drop when a brick is broken, enlarging the paddle when collected.

- **Score & Level Display:**  
  Real-time updates on score and current level are rendered on the screen.

- **Game Over Condition:**  
  The game ends when the ball falls below the paddle, displaying a "Game Over" message.

## Prerequisites

- **Java Development Kit (JDK):** Version 11 or later is recommended.
- **JavaFX SDK:**  
  This project uses JavaFX. You must import JavaFX into your IDE or add the necessary JavaFX modules as compilation arguments.  
  - Download the JavaFX SDK from [openjfx.io](https://openjfx.io).  
  - When compiling/running from the command line, include the following (adjust paths as necessary):
    ```bash
    javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.graphics,javafx.fxml -d out src/application/BrickBreaker.java
    java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.graphics,javafx.fxml -cp out application.BrickBreaker
    ```

## Installation

1. **Clone the Repository:**

 ```bash
   git clone <path to this git repo>
   cd _src/BrickBreaker
```

2. **Import the Project:**
    
    - Open your favorite Java IDE (such as IntelliJ IDEA, Eclipse, or NetBeans).
    - Import the project as a Java project.
    - Configure the JavaFX library in your IDE. Refer to your IDE’s documentation for setting up JavaFX.
3. **Compile and Run:**
    
    - **Via IDE:**  
        Run the `BrickBreaker` class which is located in the `application` package.
        
    - **Via Command Line:**  
        Use the compilation and run commands mentioned in the Prerequisites section.
        

## How to Play

- **Paddle Movement:**  
    Use the **LEFT** and **RIGHT** arrow keys to move the paddle.
    
- **Objective:**  
    Bounce the ball off the paddle to hit and break all the bricks. Watch out for power-ups that may drop from broken bricks.
    
- **Game Over:**  
    If the ball falls below the paddle, the game is over.
    

## Code Overview

- **Main Class:**  
    `BrickBreaker.java` (in the `application` package) contains the complete game logic:
    - **Game Loop:** Uses `AnimationTimer` for continuous updates.
    - **Input Handling:** Listens for key press/release events to move the paddle.
    - **Collision Detection:** Handles ball interactions with walls, paddle, bricks, and power-ups.
    - **Power-Up Mechanics:** Implements power-ups that expand the paddle when collected.
- **Rendering:**  
    The game uses a `Canvas` and `GraphicsContext` to draw the paddle, ball, bricks, power-ups, and game text.

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch:
    
    ```bash
    git checkout -b feature-new-idea
    ```
    
3. Commit your changes:
    
    ```bash
    git commit -m "Add new feature or fix bug"
    ```
    
4. Push your branch:
    
    ```bash
    git push origin feature-new-idea
    ```
    
5. Open a pull request for review.

## License

This project is licensed under the MIT License. See the LICENSE file for details.
