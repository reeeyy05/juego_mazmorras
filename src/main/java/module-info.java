module com.alejandro.alberto {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.alejandro.alberto to javafx.fxml;
    opens com.alejandro.alberto.controladores to javafx.fxml;

    exports com.alejandro.alberto;
}