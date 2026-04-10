module ru.nsu.nmashkin.task231 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.compiler;

    opens ru.nsu.nmashkin.task231 to javafx.fxml;
    exports ru.nsu.nmashkin.task231;
}