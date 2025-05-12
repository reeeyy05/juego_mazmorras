module juego.mazmorras {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.alejandro.alberto.controladores to javafx.fxml;

    exports com.alejandro.alberto;
    exports com.alejandro.alberto.modelo;
}