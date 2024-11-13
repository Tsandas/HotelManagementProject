module org.example.hotelmanagementproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.yaml.snakeyaml;

    opens org.example.hotelmanagementproject to javafx.fxml;
    exports org.example.hotelmanagementproject;
}