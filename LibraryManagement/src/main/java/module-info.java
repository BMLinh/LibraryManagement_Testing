module com.ou.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.ou.librarymanagement to javafx.fxml;
    exports com.ou.librarymanagement;
    exports com.ou.pojo;
}
