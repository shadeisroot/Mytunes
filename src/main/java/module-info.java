module com.example.mytunes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;


    opens com.example.mytunes to javafx.fxml;
    exports com.example.mytunes;
}