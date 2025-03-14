
# MyCalc - A Simple Java Calculator

MyCalc is a lightweight calculator application built in Java using AWT for the graphical interface. It supports basic arithmetic operations, including addition, subtraction, multiplication, division, and modulus, along with features like backspace, clear, toggling negative numbers, and decimal input.

## Features

- **Graphical Interface:**  
  Built using Java AWT. The calculator window features a clean layout with a display label and buttons for digits and operations.

- **Basic Arithmetic Operations:**  
  Supports addition, subtraction, multiplication, division, and modulus operations.

- **Additional Functionalities:**  
  - Toggle negative values  
  - Decimal point handling  
  - Backspace and clear functions

## Prerequisites

- **Java Development Kit (JDK):**  
  Version 8 or later is recommended.

## Installation

1. **Clone the Repository:**

   ```bash
   git clone <git path to this repository>
   cd _src/CalculatorAWT
```

2. **Compile the Code:**
    
    ```bash
    javac MyCalc.java
    ```
    
3. **Run the Application:**
    
    ```bash
    java MyCalc
    ```
    

## Usage

- **Launching the Calculator:**  
    Running the application will open a window titled "MyCalculator" with a display area and several buttons arranged for digits and operations.
    
- **Interacting with the Calculator:**
    
    - **Digit Buttons (0-9):** Click to input numbers.
    - *_Operation Buttons (+, -, _, /, %):__ Click to select an arithmetic operation.
    - **Special Function Buttons:**
        - **"+/-":** Toggle the sign of the current input.
        - **".":** Add a decimal point (only if one is not already present).
        - **"Back":** Remove the last entered digit.
        - **"CE":** Clear the current input.
        - **"=":** Calculate and display the result based on the selected operation.

## Code Overview

The entire application is implemented in a single Java file: **`MyCalc.java`**.

- **GUI Setup:**  
    Uses an AWT `Frame` with absolute positioning to arrange the display label and buttons.
    
- **Button Creation and Layout:**  
    Buttons are created with a helper method and positioned manually. Each button has an `ActionListener` attached to handle user interactions.
    
- **Event Handling:**  
    Implements `ActionListener` for button clicks and extends `WindowAdapter` to handle window closing events.
    
- **Calculation Logic:**  
    Input is collected using a `StringBuilder` and parsed to `double` values. The calculator supports chaining operations and updates the display after each calculation.
    

## Contributing

Contributions are welcome! Follow these steps to contribute:

1. Fork the repository.
2. Create a new branch:
    
    ```bash
    git checkout -b feature-name
    ```
    
3. Commit your changes:
    
    ```bash
    git commit -m "Add new feature or fix bug"
    ```
    
4. Push your branch:
    
    ```bash
    git push origin feature-name
    ```
    
5. Open a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for details.