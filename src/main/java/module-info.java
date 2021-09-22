module com.lucas.marketsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lucas.marketsystem to javafx.fxml;
    exports com.lucas.marketsystem;
}