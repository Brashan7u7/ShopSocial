package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));

        // Crear la escena con el contenido cargado desde FXML
        Scene scene = new Scene(root);

        // Configurar la escena en la ventana principal
        stage.setScene(scene);


        // Mostrar la ventana
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
