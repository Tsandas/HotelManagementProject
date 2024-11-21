module org.example.hotelmanagementproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.yaml.snakeyaml;
    requires java.desktop;

    opens org.example.hotelmanagementproject to javafx.fxml;
    opens org.example.hotelmanagementproject.Utils to javafx.base;
    exports org.example.hotelmanagementproject;
}