package ru.nsu.nmashkin.task231;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.annotation.processing.Generated;

/**
 * .
 */
@Generated("Nothing to test")
public class View extends Application {
    /**
     * .
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     * @throws Exception it does do so.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load(); // Loads FXML & instantiates Controller

        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(controller::handleKeyPress);
        stage.setTitle("Snake (FXML + MVC)");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
