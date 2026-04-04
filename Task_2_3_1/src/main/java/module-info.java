module ru.nsu.nmashkin.task231.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires java.annotation;
    requires java.compiler;


    opens ru.nsu.nmashkin.task231 to javafx.fxml;
    exports ru.nsu.nmashkin.task231;
}