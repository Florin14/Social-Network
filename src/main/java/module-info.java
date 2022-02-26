module com.example.gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.gui to javafx.fxml;
    exports com.example.gui;
    exports com.example.gui.controller;
    opens com.example.gui.controller to javafx.fxml;
    exports com.example.gui.database;
    exports com.example.gui.service;
    exports com.example.gui.repository;
    exports com.example.gui.domain;
    opens com.example.gui.database to javafx.fxml;
}