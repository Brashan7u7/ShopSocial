package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main extends Application {


    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "DJE20ben";
    public static Connection connection;



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/Diseño/Home.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static Connection RespaldoConexionDB(Connection conn){
        System.out.println("Realize con exito el respaldo de la conexion...");
        return conn;
    }
    public static void main(String[] args) {
        String username = null;
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //RespaldoConexionDB(connection);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Nombre FROM Usuario");
            if (resultSet.next()) username = resultSet.getString("Nombre");
            resultSet.close();
            statement.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        String fxmlFilePath = "src/home/Diseño/Home.fxml";
        try{
            Path path = Paths.get(fxmlFilePath);
            String content = Files.readString(path, StandardCharsets.UTF_8);
            content = content.replace("Juan", username);
            Files.writeString(path, content, StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
        }
        launch();
    }
}
