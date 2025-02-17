module org.example.cafepos {
    requires javafx.controls;
    requires javafx.fxml;



    opens org.example.cafepos to javafx.fxml;
    exports org.example.cafepos;
}