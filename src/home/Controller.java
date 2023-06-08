package home;

import home.Mapa.Principal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.PipedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;


public class Controller {
    public static final String URL = "jdbc:mysql://localhost:3306/mydb";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "DJE20ben";
    private Stage primaryStage;
    private Stage pruebaStage;

    @FXML
    private Button button;

    @FXML
    private ListView<String> productosListView;

    @FXML
    private TextField idProductosTextField;

    @FXML
    private TextField IdUbicaciónTextField;

    @FXML
    private TextField direccionTextField;

    @FXML
    private TextField latitudTextField;

    @FXML
    private TextField longitudTextField;

    @FXML
    private ComboBox<String> tiendaComboBox;









    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void BotonInicio() {
        // Lógica para el botón de inicio
    }

    @FXML
    private void BotonMapa() {
        Principal principal = new Principal();
        principal.setVisible(true);

    }
    @FXML
    private void BotonAgregarUbicacion(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Diseño/AgregarUbi.fxml"));
            Parent pruebaRoot = loader.load();
            Scene pruebaScene = new Scene(pruebaRoot);
            Controller pruebaController = loader.getController();
            pruebaController.setPrimaryStage(pruebaStage);
            pruebaStage = new Stage();
            this.primaryStage = pruebaStage; // Asignar pruebaStage a this.primaryStage
            pruebaStage.setScene(pruebaScene);
            primaryStage.close();
            pruebaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception a) {
            a.printStackTrace();
        }
    }



    @FXML
    private void BotonPerfil() {
        System.out.println("Botón de Perfil presionado");
    }

    @FXML
    public void BotonAgregar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Diseño/AgregarProductos.fxml"));
            Parent pruebaRoot = loader.load();
            Scene pruebaScene = new Scene(pruebaRoot);
            Controller pruebaController = loader.getController();
            pruebaController.setPrimaryStage(pruebaStage);
            pruebaStage = new Stage();
            this.primaryStage = pruebaStage; // Asignar pruebaStage a this.primaryStage
            pruebaStage.setScene(pruebaScene);
            primaryStage.close();
            pruebaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception a) {
            a.printStackTrace();
        }
    }

    @FXML
    private void BotonQuitar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Diseño/EliminarProductos.fxml"));
            Parent pruebaRoot = loader.load();
            Scene pruebaScene = new Scene(pruebaRoot);
            Controller pruebaController = loader.getController();
            pruebaController.setPrimaryStage(pruebaStage);
            pruebaStage = new Stage();
            this.primaryStage = pruebaStage;
            pruebaStage.setScene(pruebaScene);
            primaryStage.close();
            pruebaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception a) {
            a.printStackTrace();
        }
    }

    @FXML
    private void BotonComentario() {
    }

    @FXML
    private void BotonCompartir() {
    }

    @FXML
    public void guardarDatos() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            int idProductos = Integer.parseInt(idProductosTextField.getText());
            String nombre = "";
            double precio = 0.0;
            double descuento = 0.0;
            String sql = "INSERT INTO productos (idProductos, nombre, precio, descuento) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idProductos);
            statement.setString(2, nombre);
            statement.setDouble(3, precio);
            statement.setDouble(4, descuento);
            statement.executeUpdate();
            idProductosTextField.clear();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void AgregarUbiBD() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            int idUbicación = Integer.parseInt(IdUbicaciónTextField.getText());
            String Direccion = direccionTextField.getText();
            String Latitude = latitudTextField.getText();
            String Longitude = longitudTextField.getText();
            int Tienda_idTienda = tiendaComboBox.getSelectionModel().getSelectedIndex() + 1; // Obtener el índice seleccionado y agregar 1

            String sql = "INSERT INTO ubicación(idUbicación, Direccion, Latitude, Longitude, Tienda_idTienda) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idUbicación);
            statement.setString(2, Direccion);
            statement.setString(3, Latitude);
            statement.setString(4, Longitude);
            statement.setInt(5, Tienda_idTienda);
            statement.executeUpdate();

            // Limpiar los campos de texto después de la inserción
            IdUbicaciónTextField.clear();
            direccionTextField.clear();
            latitudTextField.clear();
            longitudTextField.clear();
            tiendaComboBox.getSelectionModel().clearSelection();

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }




    @FXML
    public void eliminarDatos() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            int idProductos = Integer.parseInt(idProductosTextField.getText());
            String sql = "DELETE FROM productos WHERE idProductos = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idProductos);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Producto eliminado con éxito.");
            } else {
                System.out.println("No se encontró un producto con el ID proporcionado.");
            }
            idProductosTextField.clear();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
