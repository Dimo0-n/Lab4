module com.example.infojucator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.infojucator to javafx.fxml;
    exports com.example.infojucator;
}