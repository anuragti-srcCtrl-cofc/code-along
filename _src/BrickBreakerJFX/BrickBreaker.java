package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BrickBreaker extends Application {

    // Game constants
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH_START = 100;
    private static final int PADDLE_HEIGHT = 15;
    private static final int BALL_RADIUS = 10;
    private static final int ROWS_START = 5;
    private static final int COLS = 10;
    private static final int BRICK_WIDTH = 70;
    private static final int BRICK_HEIGHT = 20;

    // Game variables
    private double paddleX = WIDTH / 2 - PADDLE_WIDTH_START / 2;
    private double ballX = WIDTH / 2, ballY = HEIGHT / 2;
    private double ballSpeedX = 3, ballSpeedY = 3;
    private int paddleWidth = PADDLE_WIDTH_START;
    private boolean[][] bricks;
    private boolean leftPressed = false, rightPressed = false;
    private int score = 0;
    private int level = 1;
    private boolean gameOver = false;

    // Power-ups
    private List<PowerUp> powerUps = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        // Initialize the game
        initializeBricks();

        // Create the canvas and graphics context
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a root group and add the canvas to it
        Group root = new Group(canvas);
        Scene scene = new Scene(root);

        // Set up key event listeners
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> leftPressed = true;
                case RIGHT -> rightPressed = true;
            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT -> leftPressed = false;
                case RIGHT -> rightPressed = false;
            }
        });

        // Set up the stage
        stage.setTitle("Brick Breaker with Power-Ups");
        stage.setScene(scene);
        stage.show();

        // Game loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        }.start();
    }

    private void initializeBricks() {
        int rows = ROWS_START + (level - 1); // Add more rows as levels increase
        bricks = new boolean[rows][COLS];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < COLS; col++) {
                bricks[row][col] = true;
            }
        }
    }

    private void update() {
        if (gameOver) return;

        // Move paddle
        if (leftPressed) paddleX -= 5;
        if (rightPressed) paddleX += 5;
        paddleX = Math.max(0, Math.min(WIDTH - paddleWidth, paddleX));

        // Move ball
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Ball collision with walls
        if (ballX <= 0 || ballX >= WIDTH - BALL_RADIUS) ballSpeedX *= -1;
        if (ballY <= 0) ballSpeedY *= -1;

        // Ball collision with paddle
        if (ballY >= HEIGHT - PADDLE_HEIGHT - BALL_RADIUS &&
                ballX + BALL_RADIUS > paddleX &&
                ballX < paddleX + paddleWidth) {
            ballSpeedY *= -1;
        }

        // Ball collision with bricks
        outerLoop:
        for (int row = 0; row < bricks.length; row++) {
            for (int col = 0; col < COLS; col++) {
                if (bricks[row][col]) {
                    double brickX = col * (BRICK_WIDTH + 5) + 35;
                    double brickY = row * (BRICK_HEIGHT + 5) + 50;

                    if (ballX + BALL_RADIUS > brickX &&
                            ballX < brickX + BRICK_WIDTH &&
                            ballY + BALL_RADIUS > brickY &&
                            ballY < brickY + BRICK_HEIGHT) {
                        bricks[row][col] = false; // Destroy brick
                        ballSpeedY *= -1; // Reverse ball direction
                        score += 10; // Increment score

                        // Randomly spawn a power-up
                        if (Math.random() < 0.3) {
                            powerUps.add(new PowerUp(brickX + BRICK_WIDTH / 2, brickY + BRICK_HEIGHT / 2));
                        }

                        break outerLoop;
                    }
                }
            }
        }

        // Handle power-ups
        for (PowerUp powerUp : powerUps) {
            if (powerUp.active) {
                powerUp.fall();
                if (powerUp.isCollected(paddleX, paddleWidth)) {
                    paddleWidth += 30; // Expand paddle
                    powerUp.active = false;
                }
            }
        }

        // Game over condition
        if (ballY > HEIGHT) {
            gameOver = true;
        }

        // Check for level completion
        checkLevelProgress();
    }

    private void checkLevelProgress() {
        boolean allBricksCleared = true;
        for (boolean[] row : bricks) {
            for (boolean brick : row) {
                if (brick) {
                    allBricksCleared = false;
                    break;
                }
            }
        }

        if (allBricksCleared) {
            level++;
            ballSpeedX *= 1.2; // Increase ball speed
            ballSpeedY *= 1.2;
            paddleWidth = PADDLE_WIDTH_START; // Reset paddle width
            initializeBricks(); // Load next level bricks
        }
    }

    private void render(GraphicsContext gc) {
        // Clear the canvas
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // Draw paddle
        gc.setFill(Color.BLUE);
        gc.fillRect(paddleX, HEIGHT - PADDLE_HEIGHT, paddleWidth, PADDLE_HEIGHT);

        // Draw ball
        gc.setFill(Color.RED);
        gc.fillOval(ballX, ballY, BALL_RADIUS, BALL_RADIUS);

        // Draw bricks
        gc.setFill(Color.GREEN);
        for (int row = 0; row < bricks.length; row++) {
            for (int col = 0; col < COLS; col++) {
                if (bricks[row][col]) {
                    double brickX = col * (BRICK_WIDTH + 5) + 35;
                    double brickY = row * (BRICK_HEIGHT + 5) + 50;
                    gc.fillRect(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT);
                }
            }
        }

        // Draw power-ups
        gc.setFill(Color.ORANGE);
        for (PowerUp powerUp : powerUps) {
            if (powerUp.active) {
                gc.fillOval(powerUp.x, powerUp.y, powerUp.size, powerUp.size);
            }
        }

        // Draw score and level
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(20));
        gc.fillText("Score: " + score, 10, 20);
        gc.fillText("Level: " + level, WIDTH - 100, 20);

        // Draw Game Over
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font(50));
            gc.fillText("Game Over!", WIDTH / 2 - 150, HEIGHT / 2);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    // PowerUp class
    class PowerUp {
        double x, y, size = 20;
        boolean active = true;

        PowerUp(double x, double y) {
            this.x = x;
            this.y = y;
        }

        void fall() {
            y += 0.5; // Move down
        }

        boolean isCollected(double paddleX, double paddleWidth) {
            return y + size >= HEIGHT - PADDLE_HEIGHT &&
                    x + size > paddleX &&
                    x < paddleX + paddleWidth;
        }
    }
}
