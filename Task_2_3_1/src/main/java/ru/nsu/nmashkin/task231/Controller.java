package ru.nsu.nmashkin.task231;

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
@Generated("Nothing to test")
public class Controller {
    @FXML
    private Canvas canvas;

    private Model model;
    private AnimationTimer gameLoop;
    private long lastTick = 0L;
    private final long tickDuration = 250_000_000L;

    @FXML
    private void initialize() {
        model = new Model(10, 10, 50, Direction.UP, 5, 20);
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
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw grid lines
        gc.setStroke(Color.LIGHTGRAY);
        for (int i = 0; i <= model.getGridHeight(); i++) {
            gc.strokeLine(0, i * model.getCellSize(), canvas.getWidth(), i * model.getCellSize());
        }
        for (int i = 0; i <= model.getGridWidth(); i++) {
            gc.strokeLine(i * model.getCellSize(), 0, i * model.getCellSize(), canvas.getHeight());
        }

        // Draw food
        for (Food food : model.getFoods()) {
            gc.setFill(Color.RED);
            gc.fillRect((food.coords().x() + 0.4) * model.getCellSize(),
                    (food.coords().y() + 0.4) * model.getCellSize(),
                    0.2 * model.getCellSize(), 0.2 * model.getCellSize());
        }

        // Draw snake
        for (SnakePart part : model.getSnake()) {
            switch (part.type()) {
                case BODY -> gc.setFill(Color.GREEN);
                case HEAD -> gc.setFill(Color.DARKGREEN);
                case TAIL -> gc.setFill(Color.LIGHTGREEN);
                default -> { }
            }

            gc.fillRect((part.coords().x() + 0.2) * model.getCellSize(),
                    (part.coords().y() + 0.2) * model.getCellSize(),
                    0.6 * model.getCellSize(), 0.6 * model.getCellSize());
        }
    }

    /**
     * .
     *
     * @param e event.
     */
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
