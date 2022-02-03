package mainPackage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelDatei {
	
	
	public static void schreibePersonenInExcel (ArrayList<Person> inputListePersonen, String outputExcelDateiPfad, String outputExcelDateiName, String tabelle) throws FileNotFoundException, IOException {
		
		System.out.println("Erstelle Excel-Datei...");
		
		Workbook mappe_Personen = new XSSFWorkbook(); // xlsx-Format (ab 2003)
		Sheet tabelle_Personen = mappe_Personen.createSheet(tabelle);
		// Erstelle Kopfzeile
		Row kopfZeile = tabelle_Personen.createRow(0);
		kopfZeile.createCell(0).setCellValue("id");
		kopfZeile.createCell(1).setCellValue("Vorname");
		kopfZeile.createCell(2).setCellValue("Nachname");
		kopfZeile.createCell(3).setCellValue("Alter");
		
		// Fuege Datensaetze in Tabelle ein
		int zeile = 1;
		for (Person person : inputListePersonen) {
			Row datensatz = tabelle_Personen.createRow(zeile);
			datensatz.createCell(0).setCellValue(zeile);
			datensatz.createCell(1).setCellValue(person.getVorname());
			datensatz.createCell(2).setCellValue(person.getNachname());
			datensatz.createCell(3).setCellValue(person.getAlter());
			
			zeile++;
		}
		
		// Erstelle Datei
		mappe_Personen.write(new FileOutputStream(outputExcelDateiPfad + outputExcelDateiName));
		
		mappe_Personen.close();
		
		System.out.println("Excel-Datei wurde erstellt!");
		
	}// ENDE schreibePersonenInExcel()
	
	public static void lesePersonenAusExcel (String inputExcelDateiPfad, String inputExcelDateiName, String inputTabelle , ArrayList<Person> outputListePersonen) throws InvalidFormatException, IOException {
		
		// Erstelle Objekt für die Datei
		File excelDatei = new File(inputExcelDateiPfad + inputExcelDateiName);
		// Lese Datei ein
		FileInputStream excelInput = new FileInputStream(excelDatei);
		Workbook mappe = new XSSFWorkbook(excelInput);
		Sheet tabelle = mappe.getSheet(inputTabelle);
		
		// Schleife fuer die Zeilen
		for (Row zeile : tabelle) {			
			String vorname = null;
			String nachname = null;
			int alter = 0;
			
			// Schleife fuer die Zellen
			int i_zelle = 0;
			for (Cell zelle : zeile) {
				
				// Unterscheide nach Datentyp
				switch(i_zelle) {
				
				case 0:
					vorname = zelle.getStringCellValue();
					break;
				case 1:
					nachname = zelle.getStringCellValue();
					break;
				case 2:
					alter = (int) zelle.getNumericCellValue();
					break;
				default:
					System.out.println("Fehler beim einlesen der Daten");
					break;
				
				}// ENDE switch-case				
				i_zelle++;
			}// ENDE for-Schleife für Zelle
			
			i_zelle = 0;
			
			outputListePersonen.add(new Person(vorname, nachname, alter));
			
		}// ENDE for-Schleife für Zeile
		
		
		
		mappe.close();
		
	}// ENDE lesePersonenAusExcel()
	
	public static void erzeugeTestdaten_xlsxDatei(String outputDateiPfad, String outputDateiName, int anzahlZeilen) throws FileNotFoundException, IOException {
		
		System.out.println("Erstelle Excel-Testdatei...");
		
		Workbook mappe_Personen = new XSSFWorkbook();
		Sheet tabelle_Personen = mappe_Personen.createSheet("Tabelle1");
				
		// Fuege Testdaten in Tabelle ein
		for (int i = 0; i < anzahlZeilen; i++) {
			Row datensatz = tabelle_Personen.createRow(i);
			datensatz.createCell(0).setCellValue("Vorname" + i);
			datensatz.createCell(1).setCellValue("Nachname" +i);
			datensatz.createCell(2).setCellValue(i);			
		}
		
		// Erstelle Datei
		mappe_Personen.write(new FileOutputStream(outputDateiPfad + outputDateiName));
		
		mappe_Personen.close();
		
		System.out.println("Excel-Testdatei wurde erstellt!");
		
		
		
		
	}
	
	
}// ENDE Klasse ExcelMain
