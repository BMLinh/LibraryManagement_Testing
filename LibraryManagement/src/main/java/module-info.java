module com.ou.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ou.librarymanagement to javafx.fxml;
    exports com.ou.librarymanagement;
}
