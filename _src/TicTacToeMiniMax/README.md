# Unbeatable Tic Tac Toe AI

A Java-based Tic Tac Toe game that features an unbeatable AI opponent using the **minimax algorithm with alpha-beta pruning**. The game utilizes AWT for a simple graphical interface, where **X** is drawn in **red**, **O** in **blue**, and the board features black grid lines on a light gray background.

## Features

- **Unbeatable AI:**  
  Utilizes the minimax algorithm with alpha-beta pruning to always choose the optimal move.
  
- **Graphical Interface:**  
  Built with Java AWT, featuring a clean, interactive game board.
  
- **Player Options:**  
  At the start, the game prompts the user to choose whether to play first or let the AI start.
  
- **Simple & Intuitive:**  
  Easy-to-follow gameplay with immediate feedback through mouse interactions.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Code Overview](#code-overview)
- [Contributing](#contributing)
- [License](#license)

## Installation

### Prerequisites

- **Java Development Kit (JDK)** version 8 or later.

### Steps

1. **Clone the Repository:**

   ```bash
   git clone <git path for this repo>
   cd TicTacToeMiniMax

   ```

2. **Compile the Code:**

   ```bash
   javac TicTacToe.java
   ```

3. **Run the Game:**

   ```bash
   java TicTacToe
   ```

## Usage

- When the game starts, a dialog box will prompt you to decide whether you want to go first.
- Click on the board to make your move.
- The AI will automatically execute its move based on the minimax algorithm.
- At the end of the game (win, loss, or draw), you'll be offered the option to restart the game.

## Code Overview

The entire project is implemented in the single file **`TicTacToe.java`**.

### Key Components

- **Game Board:**  
  A 3x3 board represented by a 2D array of characters (`char[][]`).

- **Graphical User Interface (GUI):**  
  Uses Java AWT to draw the game board and the marks. The grid lines are drawn in black, with **X** rendered in red and **O** in blue.

- **Minimax Algorithm with Alpha-Beta Pruning:**  
  - **Minimax Function:** Recursively computes the optimal move for the AI, simulating all possible moves.
  - **Alpha-Beta Pruning:** Optimizes the search by eliminating branches that cannot affect the final decision.
  - **Evaluation Function:** Determines the game state (win, loss, or draw) for terminal states.
  
- **User Interaction:**  
  Mouse events capture the human player's moves and update the game state accordingly.

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m "Add feature"`).
4. Push your branch (`git push origin feature-name`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
