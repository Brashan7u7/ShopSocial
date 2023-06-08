package home.BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "DJE20ben";

    public static void main(String[] args) {
        try {
            // Establecer conexión
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Conexión exitosa");

            // Ejecutar consulta
            String sql = "SELECT * FROM usuario";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();


            // Recorrer resultados
            while (resultSet.next()) {
                // Obtener valores de columnas

                String nombre = resultSet.getString("nombre");

                // ...

                // Hacer algo con los datos obtenidos
                System.out.println("Nombre: " + nombre);
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
