	package com.programa_costos;

import com.programa_costos.view.VentanaPrincipal;

public class MainApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}
