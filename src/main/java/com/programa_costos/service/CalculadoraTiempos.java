package com.programa_costos.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.Iterator;

public class CalculadoraTiempos {

	private static final String FILE_PATH = "/reglas_tiempos_produccion.xlsx";

	private static int buscarTiempo(String tipoTiempo, int largo, int ancho, int grueso, int numPiezas, int gruesoTotal,
			int numCapas, int profundidad) {
		try (InputStream is = CalculadoraTiempos.class.getResourceAsStream(FILE_PATH);
				Workbook workbook = new XSSFWorkbook(is)) {

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			if (rows.hasNext())
				rows.next(); // saltar encabezados

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
				int numCapasFila = (int) row.getCell(9).getNumericCellValue();
				int profMin = (int) row.getCell(10).getNumericCellValue();
				int profMax = (int) row.getCell(11).getNumericCellValue();

				boolean coincide = (largo >= largoMin && largo <= largoMax) && (ancho >= anchoMin && ancho <= anchoMax)
						&& (grueso <= gruesoMax) && (numPiezas >= numPiezasMin && numPiezas <= numPiezasMax)
						&& (gruesoTotal >= gruesoTotalMin && gruesoTotal <= gruesoTotalMax)
						&& (numCapas == numCapasFila) && (profundidad >= profMin && profundidad <= profMax);

				if (coincide) {
					switch (tipoTiempo) {
					case "Preparación de máquina":
						return (int) row.getCell(12).getNumericCellValue();
					case "Mecanizado":
						return (int) row.getCell(13).getNumericCellValue();
					case "Pegado":
						int pegadoCol = 14 + (numCapas - 1);
						return (int) row.getCell(pegadoCol).getNumericCellValue();
					default:
						return 0;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int calcularTiempoPreparacion(int largo, int ancho, int grueso, int numPiezas, int gruesoTotal,
			int numCapas, int profundidad) {
		return buscarTiempo("Preparación de máquina", largo, ancho, grueso, numPiezas, gruesoTotal, numCapas,
				profundidad);
	}

	public static int calcularTiempoMecanizado(int largo, int ancho, int grueso, int numPiezas, int gruesoTotal,
			int numCapas, int profundidad) {
		return buscarTiempo("Mecanizado", largo, ancho, grueso, numPiezas, gruesoTotal, numCapas, profundidad);
	}

	public static int calcularTiempoPegado(int numCapas) {
		// Si quieres usar Excel para pegado, descomenta la siguiente línea y comenta la
		// que está abajo:
		// return buscarTiempo("Pegado", 0, 0, 0, 0, 0, numCapas, 0);

		// Cálculo simple fijo si el pegado es 15 minutos por capa adicional:
		if (numCapas <= 1)
			return 0;
		return 15 * (numCapas - 1);
	}
}
