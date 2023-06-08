package home.Mapa;
import org.jxmapviewer.*;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Collections;

public class Principal extends JFrame implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private JXMapViewer mapViewer;
    private GeoPosition initialPosition;
    private Point dragStart;
    private boolean dragging;

    public static void main(String[] args) {
        Principal p = new Principal();
    }

    public Principal() {
        this.setBounds(100, 100, 1000, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Mapas");
        getContentPane().setBackground(new Color(10, 0, 0));

        InterfazInicial();

        setVisible(true);
    }

    private void InterfazInicial() {
        mapViewer = new JXMapViewer();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        initialPosition = new GeoPosition(17.0654, -96.7237); // Coordenadas de Oaxaca, México

        // Set the focus
        mapViewer.setZoom(10); // Zoom inicial
        mapViewer.setCenterPosition(initialPosition);

        mapViewer.setBounds(0, 0, 1000, 800);
        mapViewer.addMouseListener(this);
        mapViewer.addMouseMotionListener(this);
        mapViewer.addMouseWheelListener(this);
        add(mapViewer);

        // Obtener las coordenadas desde la base de datos
        GeoPosition position = obtenerCoordenadasDesdeBaseDeDatos();

        // Crear un Waypoint con la GeoPosition
        Waypoint waypoint = new DefaultWaypoint(position);

        // Crear un WaypointPainter
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(Collections.singleton(waypoint));

        // Agregar el WaypointPainter al JXMapViewer
        mapViewer.setOverlayPainter(waypointPainter);
    }

    private GeoPosition obtenerCoordenadasDesdeBaseDeDatos() {
        // Realizar la conexión a la base de datos y obtener las coordenadas

        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "DJE20ben";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Realizar la consulta para obtener las coordenadas desde la base de datos
            String query = "SELECT latitude, longitude FROM `ubicación` WHERE idUbicación = ?";
            int id = 1; // ID del registro deseado

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        return new GeoPosition(latitude, longitude);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // En caso de error o si no se encuentra el registro en la base de datos, regresar una posición predeterminada
        return new GeoPosition(0, 0);
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint();
        dragging = true;
        mapViewer.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    public void mouseReleased(MouseEvent e) {
        dragging = false;
        mapViewer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            Point dragEnd = e.getPoint();
            int diffX = dragStart.x - dragEnd.x;
            int diffY = dragStart.y - dragEnd.y;
            GeoPosition newCenter = mapViewer.convertPointToGeoPosition(new Point(mapViewer.getWidth() / 2 + diffX, mapViewer.getHeight() / 2 + diffY));
            mapViewer.setCenterPosition(newCenter);
        }
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        int currentZoom = mapViewer.getZoom();
        int newZoom = currentZoom + notches;
        mapViewer.setZoom(newZoom);
    }
}
