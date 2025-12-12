//para definir el módulo y sus dependencias

module com.example.MP4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.MP4.controller to javafx.fxml;

    exports com.example.MP4;
}