# Boids Simulation in Java

A simple Boids simulation program written in Java that visually demonstrates how a group of boids (simulated birds) interact based on rules of separation, alignment, and cohesion.

## Table of Contents
- [About](#about)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Details on Controls](#details-on-controls)
- [How It Works](#how-it-works)
- [Requirements](#requirements)

## About
This project simulates the behavior of a flock of boids. Each boid follows basic rules to avoid crowding (`separation`), move toward other boids (`cohesion`), and align with their neighbors (`alignment`). The simulation provides sliders to adjust these behaviors and a toggle to switch between food and predator mode.

## Features
- Adjustable sliders to control alignment, cohesion, and separation
- Toggle between food and predator modes with a button
- Real-time mouse control to affect boids’ movement

## Getting Started
1. **Clone this repository**:
   ```bash
   git clone <path to this repo>
   cd _src/Boids
   ```
2. **Open the project in your favorite IDE** (like IntelliJ or Eclipse).

3. **Run the `BoidsSimulation` class**.

## Usage
1. **Adjust the sliders** to see how different behaviors affect the boids.
2. **Move your mouse** in the window to attract or repel boids based on the toggle mode.
3. **Press the toggle button** to switch between `Food Mode` (attract boids) and `Predator Mode` (repel boids).

## Details on Controls
- **Alignment**: Controls how much each boid tries to align its direction with its neighbors.
- **Cohesion**: Controls how much each boid tries to stay close to its neighbors.
- **Separation**: Controls how much each boid tries to keep a safe distance from others.
- **Mouse Force**: Adjusts how strongly the boids react to the mouse position.
- **Toggle Button**: Switches between food and predator mode.

## How It Works
- Each boid is represented by a simple point that follows basic rules of alignment, cohesion, and separation with nearby boids.
- The **`paintComponent`** method draws each boid on the screen and updates their movement on every frame.

## Requirements
- Java Development Kit (JDK) version 8 or higher
- Recommended IDE: IntelliJ IDEA, Eclipse