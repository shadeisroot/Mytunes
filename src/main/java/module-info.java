module com.example.mytunes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;


    exports com.example.mytunes.GUI;
    opens com.example.mytunes.GUI to javafx.fxml;
    exports com.example.mytunes.Data;
    opens com.example.mytunes.Data to javafx.fxml;
    exports com.example.mytunes.Business;
    opens com.example.mytunes.Business to javafx.fxml;
}