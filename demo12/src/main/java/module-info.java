module com.example.demo12 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo12 to javafx.fxml;
    exports com.example.demo12;
}