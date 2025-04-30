module org.example.cafepos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires org.apache.pdfbox;
    requires java.desktop;


    opens org.example.cafepos to javafx.fxml;
    exports org.example.cafepos;
}