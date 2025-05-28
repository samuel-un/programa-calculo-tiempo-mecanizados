package com.programa_costos.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.Iterator;

public class CalculadoraTiempos {

	private static final String FILE_PATH = "/reglas_tiempos_produccion.xlsx";

	private static int buscarTiempo(String tipoTiempo, int largo, int ancho, int grueso, int numPiezas,
			int gruesoTotal, int numCapas, int profundidad) throws ReglaNoEncontradaException {
		try (InputStream is = CalculadoraTiempos.class.getResourceAsStream(FILE_PATH);
				Workbook workbook = new XSSFWorkbook(is)) {

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			if (rows.hasNext())
				rows.next(); // Saltar encabezados

			while (rows.hasNext()) {
				Row row = rows.next();

				int largoMin = (int) row.getCell(0).getNumericCellValue();
				int largoMax = (int) row.getCell(1).getNumericCellValue();
				int anchoMin = (int) row.getCell(2).getNumericCellValue();
				int anchoMax = (int) row.getCell(3).getNumericCellValue();
				int gruesoMax = (int) row.getCell(4).getNumericCellValue();
				int numPiezasMin = (int) row.getCell(5).getNumericCellValue();
				int numPiezasMax = (int) row.getCell(6).getNumericCellValue();
				int gruesoTotalMin = (int) row.getCell(7).getNumericCellValue();
				int gruesoTotalMax = (int) row.getCell(8).getNumericCellValue();
				int numCapasMin = (int) row.getCell(9).getNumericCellValue();
				int numCapasMax = (int) row.getCell(10).getNumericCellValue();
				int profMin = (int) row.getCell(11).getNumericCellValue();
				int profMax = (int) row.getCell(12).getNumericCellValue();

				boolean coincide = (largo >= largoMin && largo <= largoMax)
						&& (ancho >= anchoMin && ancho <= anchoMax)
						&& (grueso <= gruesoMax)
						&& (numPiezas >= numPiezasMin && numPiezas <= numPiezasMax)
						&& (gruesoTotal >= gruesoTotalMin && gruesoTotal <= gruesoTotalMax)
						&& (numCapas >= numCapasMin && numCapas <= numCapasMax)
						&& (profundidad >= profMin && profundidad <= profMax);

				if (coincide) {
					switch (tipoTiempo) {
						case "Preparación de máquina":
							return (int) row.getCell(13).getNumericCellValue();
						case "Mecanizado":
							return (int) row.getCell(14).getNumericCellValue();
						case "Pegado":
							return numCapas > 1 ? 15 * (numCapas - 1) : 0;
						default:
							throw new ReglaNoEncontradaException("Tipo de tiempo no reconocido: " + tipoTiempo);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReglaNoEncontradaException("Error al leer el archivo de reglas.");
		}
		throw new ReglaNoEncontradaException(
				"No se encontró ninguna fila en la tabla que coincida con los valores ingresados.");
	}

	public static int calcularTiempoPreparacion(int largo, int ancho, int grueso, int numPiezas, int gruesoTotal,
			int numCapas, int profundidad) throws ReglaNoEncontradaException {
		return buscarTiempo("Preparación de máquina", largo, ancho, grueso, numPiezas, gruesoTotal, numCapas,
				profundidad);
	}

	public static int calcularTiempoMecanizado(int largo, int ancho, int grueso, int numPiezas, int gruesoTotal,
			int numCapas, int profundidad) throws ReglaNoEncontradaException {
		return buscarTiempo("Mecanizado", largo, ancho, grueso, numPiezas, gruesoTotal, numCapas, profundidad);
	}

	public static int calcularTiempoPegado(int largo, int ancho, int grueso, int numPiezas, int gruesoTotal,
			int numCapas, int profundidad) throws ReglaNoEncontradaException {
		return buscarTiempo("Pegado", largo, ancho, grueso, numPiezas, gruesoTotal, numCapas, profundidad);
	}
}
// This code is part of a Java application that calculates production times based on input parameters.