public class ModuleInfo {

}
//module org.js.programmingwindowapplications {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires org.kordamp.bootstrapfx.core;
//    requires jakarta.persistence;
//    requires org.hibernate.orm.core;
//    requires mysql.connector.j;
//    requires jakarta.transaction;
//    requires spring.context;
//    requires spring.boot;
//    requires spring.boot.autoconfigure;
//
//    opens org.js.programmingwindowapplications.db.entities to org.hibernate.orm.core;
//    opens org.js.programmingwindowapplications to javafx.fxml;
//    exports org.js.programmingwindowapplications;
//    exports org.js.programmingwindowapplications.animalshelterUI;
//    exports org.js.programmingwindowapplications.animalshelter;
//    opens org.js.programmingwindowapplications.animalshelterUI to javafx.fxml, javafx.graphics;
//}