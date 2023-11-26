module com.example.relatoriotetoarduino {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.jfree.jfreechart;
    requires javafx.swing;


    opens com.example.relatoriotetoarduino to javafx.fxml;
    exports com.example.relatoriotetoarduino;
}