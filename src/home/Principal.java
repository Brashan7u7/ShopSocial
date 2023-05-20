package home;

import org.jxmapviewer.*;
import org.jxmapviewer.painter.*;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;
import java.util.ArrayList;

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
