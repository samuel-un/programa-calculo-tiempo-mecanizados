package com.programa_costos.view;

import com.programa_costos.service.CalculadoraTiempos;
import com.programa_costos.service.ReglaNoEncontradaException;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private JTextField txtLargo, txtAncho, txtGrueso, txtNumPiezas, txtGruesoTotal, txtNumCapas, txtProfundidad;
	private JTextArea txtResultado;

	public VentanaPrincipal() {
		setTitle("Calculadora de Tiempos de Producción");
		setSize(400, 480);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JLabel[] etiquetas = {
				new JLabel("Largo (cm):"), new JLabel("Ancho (cm):"), new JLabel("Grueso (cm):"),
				new JLabel("Número de piezas mecanizadas:"), new JLabel("Grueso total del molde (cm):"),
				new JLabel("Número de capas:"), new JLabel("Profundidad máxima (cm):")
		};

		JTextField[] campos = {
				txtLargo = new JTextField(), txtAncho = new JTextField(), txtGrueso = new JTextField(),
				txtNumPiezas = new JTextField(), txtGruesoTotal = new JTextField(), txtNumCapas = new JTextField(),
				txtProfundidad = new JTextField()
		};

		for (int i = 0; i < etiquetas.length; i++) {
			etiquetas[i].setBounds(20, 20 + i * 40, 200, 25);
			campos[i].setBounds(220, 20 + i * 40, 120, 25);
			add(etiquetas[i]);
			add(campos[i]);
		}

		JButton btnCalcular = new JButton("Calcular tiempos");
		btnCalcular.setBounds(120, 310, 150, 30);
		btnCalcular.addActionListener(this::calcular);
		add(btnCalcular);

		txtResultado = new JTextArea();
		txtResultado.setBounds(20, 350, 340, 70);
		txtResultado.setEditable(false);
		add(txtResultado);
	}

	private void calcular(ActionEvent e) {
		try {
			int largo = Integer.parseInt(txtLargo.getText());
			int ancho = Integer.parseInt(txtAncho.getText());
			int grueso = Integer.parseInt(txtGrueso.getText());
			int numPiezas = Integer.parseInt(txtNumPiezas.getText());
			int gruesoTotal = Integer.parseInt(txtGruesoTotal.getText());
			int numCapas = Integer.parseInt(txtNumCapas.getText());
			int profundidad = Integer.parseInt(txtProfundidad.getText());

			int tiempoPreparacion = CalculadoraTiempos.calcularTiempoPreparacion(largo, ancho, grueso, numPiezas,
					gruesoTotal, numCapas, profundidad);
			int tiempoMecanizado = CalculadoraTiempos.calcularTiempoMecanizado(largo, ancho, grueso, numPiezas,
					gruesoTotal, numCapas, profundidad);
			int tiempoPegado = CalculadoraTiempos.calcularTiempoPegado(largo, ancho, grueso, numPiezas, gruesoTotal,
					numCapas, profundidad);

			txtResultado.setText(
					"Tiempo de preparación de máquina: " + tiempoPreparacion + " minutos\n" +
							"Tiempo de mecanizado: " + tiempoMecanizado + " minutos\n" +
							"Tiempo de pegado total: " + tiempoPegado + " minutos");
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Por favor ingresa solo números válidos en todos los campos.");
		} catch (ReglaNoEncontradaException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de cálculo", JOptionPane.ERROR_MESSAGE);
			txtResultado.setText(""); // Limpiar resultado
		}
	}
}
