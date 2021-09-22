module com.lucas.marketsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;


    opens com.lucas.marketsystem to javafx.fxml;
    exports com.lucas.marketsystem;
    exports com.lucas.marketsystem.repository;
    opens com.lucas.marketsystem.repository to javafx.fxml;
}