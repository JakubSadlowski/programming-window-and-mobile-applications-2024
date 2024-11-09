module org.js.programmingwindowapplications {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.js.programmingwindowapplications to javafx.fxml;
    exports org.js.programmingwindowapplications;
}