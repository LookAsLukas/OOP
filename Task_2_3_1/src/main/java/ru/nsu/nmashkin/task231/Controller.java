package ru.nsu.nmashkin.task231;

import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javax.annotation.processing.Generated;

/**
 * .
 */
public class Controller {
    @FXML
    private Canvas canvas;

    private Model model;
    private AnimationTimer gameLoop;
    private long lastTick = 0L;
    private final long tickDuration = 250_000_000L;
    private GraphicsContext gc;

    @FXML
    private void initialize() {
        HashMap<SnakePartType, Color> snakeColoring = new HashMap<>();
        snakeColoring.put(SnakePartType.HEAD, Color.DARKGREEN);
        snakeColoring.put(SnakePartType.BODY, Color.GREEN);
        snakeColoring.put(SnakePartType.TAIL, Color.LIGHTGREEN);

        HashMap<SnakePartType, Color> botColoring = new HashMap<>();
        botColoring.put(SnakePartType.HEAD, Color.DARKBLUE);
        botColoring.put(SnakePartType.BODY, Color.BLUE);
        botColoring.put(SnakePartType.TAIL, Color.LIGHTBLUE);

        model = new Model(10, 10, 50,
                Direction.UP, 5, 20,
                snakeColoring, botColoring);
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastTick >= tickDuration) {
                    MoveResult result = model.move();
                    if (result != MoveResult.NEUTRAL) {
                        stop();
                        gameOver(result);
                    }

                    render();
                    lastTick = now;
                }
            }
        };

        gc = canvas.getGraphicsContext2D();
        render();
        canvas.requestFocus();
        gameLoop.start();
    }

    private void gameOver(MoveResult result) {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Game Over");
            alert.setHeaderText("Game Over! You " + result);
            alert.setContentText("Final Score: " + model.getScore());

            ButtonType quitBtn = new ButtonType("Quit");
            alert.getButtonTypes().setAll(quitBtn);

            alert.showAndWait();
            Platform.exit();
        });
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawGrid();
        drawFoods();
        if (model.getBot().isDead()) {
            drawBot();
            drawSnake();
        } else {
            drawSnake();
            drawBot();
        }
    }

    private void drawGrid() {
        gc.setStroke(Color.LIGHTGRAY);
        for (int i = 0; i <= model.getGridHeight(); i++) {
            gc.strokeLine(0, i * model.getCellSize(), canvas.getWidth(), i * model.getCellSize());
        }
        for (int i = 0; i <= model.getGridWidth(); i++) {
            gc.strokeLine(i * model.getCellSize(), 0, i * model.getCellSize(), canvas.getHeight());
        }
    }

    private void drawFoods() {
        for (Food food : model.getFoods()) {
            gc.setFill(Color.RED);
            gc.fillRect((food.coords().x() + 0.4) * model.getCellSize(),
                    (food.coords().y() + 0.4) * model.getCellSize(),
                    0.2 * model.getCellSize(), 0.2 * model.getCellSize());
        }
    }

    private void drawSnake() {
        for (SnakePart part : model.getSnake().getParts()) {
            gc.setFill(model.getSnake().getColoring().get(part.type()));

            gc.fillRect((part.coords().x() + 0.15) * model.getCellSize(),
                    (part.coords().y() + 0.15) * model.getCellSize(),
                    0.7  * model.getCellSize(), 0.7 * model.getCellSize());
        }
    }

    private void drawBot() {
        for (SnakePart part : model.getBot().getParts()) {
            gc.setFill(model.getBot().getColoring().get(part.type()));

            gc.fillRect((part.coords().x() + 0.25) * model.getCellSize(),
                    (part.coords().y() + 0.25) * model.getCellSize(),
                    0.5 * model.getCellSize(), 0.5 * model.getCellSize());
        }
    }

    /**
     * .
     *
     * @param e event.
     */
    @Generated("Nothing to test")
    public void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case W, UP    -> model.setDirection(Direction.UP);
            case S, DOWN  -> model.setDirection(Direction.DOWN);
            case A, LEFT  -> model.setDirection(Direction.LEFT);
            case D, RIGHT -> model.setDirection(Direction.RIGHT);
            default -> { }
        }
    }
}
